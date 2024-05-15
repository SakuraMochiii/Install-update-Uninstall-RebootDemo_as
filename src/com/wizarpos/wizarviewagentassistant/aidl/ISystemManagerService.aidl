package com.wizarpos.wizarviewagentassistant.aidl;
interface ISystemManagerService{
   boolean uninstall(String packageName,String adminPassword);
   boolean install(String apkPath,String adminPassword);
   boolean update(String apkPath,String adminPassword);
   boolean reboot(String adminPassword);
}