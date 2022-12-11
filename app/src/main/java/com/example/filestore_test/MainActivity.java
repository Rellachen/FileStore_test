package com.example.filestore_test;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.example.filestore_test.data.NoticeDialogFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends FragmentActivity implements NoticeDialogFragment.NoticeDialogListener {

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        findViewById(R.id.write).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog("FileWrite Dialog", "确定要写入以上内容吗？", 1);
            }
        });

        findViewById(R.id.read).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoticeDialog("FileReade Dialog", "确定要读取此文件内容吗？", 2);
            }
        });


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
            Toast.makeText(this, "请输入写入内容！", Toast.LENGTH_SHORT).show();
            return;
        }
        BufferedWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(writeContents);
            Toast.makeText(this, "文件已写入！", Toast.LENGTH_SHORT).show();
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
        String filename = "srcFile";
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
            Toast.makeText(this, "文件已读出！", Toast.LENGTH_SHORT).show();
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



    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }

    private void showNoticeDialog(String title, String message, int type) {
        DialogFragment dialogFragment = new NoticeDialogFragment(title, message, type);
        dialogFragment.show(getSupportFragmentManager(), "");
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int type) {
        if (type == 1) {
            writeFile();
        } else if (type == 2) {
            readFile();
        }

        dialog.dismiss();

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog, int type) {
        dialog.dismiss();
    }

    public Activity getActivity() {
        return activity;
    }



}