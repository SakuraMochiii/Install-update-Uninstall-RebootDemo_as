package com.example.test;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.wizarpos.wizarviewagentassistant.aidl.ISystemManagerService;

import java.io.File;

public class UnInstallController {

    private static final String DEFAULT_PWD = "";
    private static final String TAG = "uninstallService";
    private static UnInstallController singleton;
    private int state = 0;// 0 , 1: install , 2:uninstall , 3:reboot
    private ISystemManagerService uninstallService;
    private UninstallServiceConnection connection = null;
    private Context host;
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (state == 1) {
                try {
                    String sourceFilePath = MainActivity.PATH;
                    File file = new File(sourceFilePath);
                    Log.d(TAG, "file = " + file.exists());
                    boolean isSuccess = uninstallService.install(sourceFilePath, DEFAULT_PWD);
                    Log.d(TAG, "unInstallSilent sourceFilePath = " + sourceFilePath);
                    Log.d(TAG, "installSilent isSuccess = " + isSuccess);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    host.unbindService(connection);
                }
            } else if (state == 2) {
                try {
                    boolean isSuccess = uninstallService.uninstall("com.cloudpos.apidemo.activity", DEFAULT_PWD);
                    Log.d(TAG, "unInstallSilent isSuccess = " + isSuccess);
                    Log.d(TAG, "installSilent isSuccess = " + isSuccess);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    host.unbindService(connection);
                }
            } else if (state == 3) {
                try {
                    boolean isSuccess = uninstallService.reboot(DEFAULT_PWD);
                    Log.d(TAG, "installSilent isSuccess = " + isSuccess);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    host.unbindService(connection);
                }
            } else if (state == 4) {
                try {
                    String sourceFilePath = MainActivity.PATH;
                    File file = new File(sourceFilePath);
                    Log.d(TAG, "update file = " + file.exists() + " DEFAULT_PWD = " + DEFAULT_PWD);
                    boolean isSuccess = uninstallService.update(sourceFilePath, DEFAULT_PWD);
                    Log.d(TAG, "installSilent isSuccess = " + isSuccess);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } finally {
                    host.unbindService(connection);
                }
            }
            Log.d(TAG, "" + msg.obj);

        }
    };

    private UnInstallController(Context host) {
        this.host = host;
        Log.d(TAG, "create UnInstallController");
    }

    public static UnInstallController getInstance(Context host) {
        if (singleton == null) {
            singleton = new UnInstallController(host);
        }

        return singleton;
    }

    public boolean aidlConnection(int state) {
        this.state = state;
        Intent intent = new Intent();
        ComponentName comp = new ComponentName(
                "com.wizarpos.wizarviewagentassistant",
                "com.wizarpos.wizarviewagentassistant.SilenceService");
        intent.setComponent(comp);
        connection = new UninstallServiceConnection();
        boolean isSuccess = host.bindService(intent, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "invoke method! isSuccess = " + isSuccess);

        if (uninstallService == null) {
            Log.d(TAG, "uninstallService is null");
        }
        return isSuccess;
    }

    private class UninstallServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            uninstallService = ISystemManagerService.Stub.asInterface(service);
            Message msg = new Message();
            msg.obj = "bind success!";
            myHandler.sendMessage(msg);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            uninstallService = null;
        }
    }
}
