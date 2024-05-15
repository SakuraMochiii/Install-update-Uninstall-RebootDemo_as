package com.example.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.test.utils.FileUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends Activity implements OnClickListener {


    public static final String TAG = "APPDEMO";

    public static final String PACKAGE_NAME = "com.wizarpos.wizarviewagentassistant";

    public static final String CLASS_NAME = "com.wizarpos.wizarviewagentassistant.SilenceUnInstallAPPService";

    private static final String APP_NAME = "APIDemoCloudPos.apk";

    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_NAME;

    public static boolean writeFile(String filePath, String content, boolean append) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//		PATH = Environment.getDataDirectory().getAbsolutePath() + "/data/"+this.getPackageName()+"/files/"+APP_NAME;
        setContentView(R.layout.activity_main);
        Button btn = (Button) this.findViewById(R.id.btn);
        btn.setOnClickListener(this);

        Button btn1 = (Button) this.findViewById(R.id.btn2);
        btn1.setOnClickListener(this);

        Button btn2 = (Button) this.findViewById(R.id.btn3);
        btn2.setOnClickListener(this);

        Button update_Btn = (Button) this.findViewById(R.id.btn4_update);
        update_Btn.setOnClickListener(this);

        boolean isExist = FileUtil.isFileExist(PATH);
        if (!isExist) {
            copy(APP_NAME, PATH);
        } else {

        }

    }

    @SuppressLint("WorldReadableFiles")
    public void copy(String src, String dst) {
        try {
            InputStream inputStream = getAssets().open(src);
            FileUtil.writeFile(dst, inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        ComponentName componetName = new ComponentName(PACKAGE_NAME, CLASS_NAME);
        intent.setComponent(componetName);
        startService(intent);

        UnInstallController con = UnInstallController.getInstance(this);
//		
        int index = v.getId();
        if (index == R.id.btn) {
            con.aidlConnection(1);
        } else if (index == R.id.btn2) {
            con.aidlConnection(2);
        } else if (index == R.id.btn3) {
            con.aidlConnection(3);
        } else if (index == R.id.btn4_update) {
            con.aidlConnection(4);
        }

    }


}
