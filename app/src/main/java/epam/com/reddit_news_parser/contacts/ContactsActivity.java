package epam.com.reddit_news_parser.contacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import epam.com.reddit_news_parser.R;
import epam.com.reddit_news_parser.entities.News;

/**
 * Created by yuri on 1/10/18.
 */

public class ContactsActivity extends AppCompatActivity {
    private static int REQUEST_CODE_READ_CONTACTS = 1;
    private List<String> contacts = new ArrayList<>();
    private News news;
    private boolean fromInstanceState = false;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contacts);

        if (savedInstanceState != null) {
            fromInstanceState = true;
            news = savedInstanceState.getParcelable("news");
            contacts = savedInstanceState.getStringArrayList("contacts");
        }

        final ListView contactList = findViewById(R.id.contactList);

        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.READ_CONTACTS},
                REQUEST_CODE_READ_CONTACTS
        );

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contacts);
        contactList.setAdapter(adapter);

        Intent intent = getIntent();
        news = intent.getParcelableExtra("news");

        contactList.setOnItemClickListener((parent, view, position, id) -> {
            Intent sendIntent = new Intent();
            StringBuilder sendBuilder = new StringBuilder();
            sendBuilder.append(news.getTitle()).append("\n\n").append(news.getUrl());

            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, sendBuilder.toString());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.dialog_share_news)));
        });

        // TODO: 1/10/18 сохранение в БД (всегда после успешного взятия из сети и при подгрузке, предварительно очищать старые записи) и взятие из БД в офлайн-режиме (при ошибке сети)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (!fromInstanceState) {
                loadContacts();
            } else {
                updateContacts();
                fromInstanceState = false;
            }
            fromInstanceState = false;
        } else {
            Toast.makeText(this, R.string.contacts_permission, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateContacts() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("news", news);
        outState.putStringArrayList("contacts", new ArrayList<>(contacts));
    }

    private void loadContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String contact = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                contacts.add(contact);
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }
}
