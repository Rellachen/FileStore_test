package com.example.filestore_test.data;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.filestore_test.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_store);
    }

    public File getFileDir() {
        File filesDir = getFilesDir();
        Log.d("File", "FileDir:" + filesDir.getAbsolutePath());
        return filesDir;
    }

    public File getCacheDir() {
        File cacheDir = getCacheDir();
        Log.d("File", "CacheDir:" + cacheDir.getAbsolutePath());
        return cacheDir;
    }

    public void writeFile() {
        String filename = "srcFile";
        EditText editText = findViewById(R.id.edit_text);
        String writeContents = editText.getText().toString();

        if (writeContents.isEmpty()) {
            Log.d("File", "writeContents is Empty!");
            return;
        }
        BufferedWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(writeContents);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("File", e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readFile() {
        Log.d("File", "read file start");
        String filename = "sccFile";
        BufferedReader reader = null;
        try {
            FileInputStream fis = openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(fis));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            Log.d("File", stringBuilder.toString());
            EditText editText = findViewById(R.id.edit_text);
            editText.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("File", e.getMessage());
        } finally {
            Log.d("File", "read file finally");
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}