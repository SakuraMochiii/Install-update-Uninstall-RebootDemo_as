## Overview

`Install&update&Uninstall&RebootDemo_as` provides the AIDL interface to help the third-party app to
implement, silence, install/uninstall/update app, or reboot device.

Permissions: 
The app declares the following permissions in the manifest.
android.permission.CLOUDPOS_INSTALL_SILENCE silence install
android.permission.CLOUDPOS_UNINSTALL_SILENCE silence uninstall
android.permission.CLOUDPOS_REBOOT silence reboot

## Features

- **Install silently**: boolean install(String apkPath,String adminPassword);
- **installApkFile**: int installApkFile(String apkFilePath);
- **Uninstall**: boolean uninstall(String packageName,String adminPassword);
- **Updates silently**: boolean update(String apkPath,String adminPassword);
- **Reboots silently**: boolean reboot(String adminPassword);;
