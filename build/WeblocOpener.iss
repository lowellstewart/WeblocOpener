; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "WeblocOpener"
#define MyAppPublisher "Eugene Zrazhevsky"
#define MyAppURL "https://benchdoos.github.io/"
#define MyAppExeName "WeblocOpener.exe"
#define MyAppIconsFile "icons.icl"
#define MyAppSourcePath "F:\Developer\JAVA\WeblocOpener"
#define MyAppAdditionalPath "F:\Developer\JAVA\WeblocOpener\build\installer"
#define ImagesPath "F:\Developer\JAVA\WeblocOpener\build\installer\images"
#define ApplicationVersion GetFileVersion('F:\Developer\JAVA\WeblocOpener\build\WeblocOpener.exe')
#define ApplicationCopyright GetFileCopyright('F:\Developer\JAVA\WeblocOpener\build\WeblocOpener.exe')

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{F1300E10-BBB2-4695-AC2F-3D58DC0BC0A6}
AppName={#MyAppName}
AppVersion={#ApplicationVersion}
VersionInfoVersion=1.0
AppCopyright={#ApplicationCopyright}

AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={pf}\{#MyAppName}
DefaultGroupName={#MyAppName}
OutputDir={#MyAppSourcePath}\target
OutputBaseFilename=WeblocOpenerSetup
LicenseFile={#MyAppAdditionalPath}\languages\license\License.rtf

SetupIconFile={#ImagesPath}\icon.ico
WizardImageFile={#ImagesPath}\WizardImageFile.bmp
WizardSmallImageFile={#ImagesPath}\WizardSmallImageFile.bmp
UninstallDisplayIcon={app}\{#MyAppIconsFile},2


ArchitecturesInstallIn64BitMode=x64

Compression=lzma
SolidCompression=yes
ChangesAssociations=yes
PrivilegesRequired=admin

ShowLanguageDialog=auto
DisableProgramGroupPage=yes

[Registry]
; File association
Root: HKCR; Subkey: ".webloc"; ValueType: string; ValueName: ""; ValueData: "Webloc"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc"; ValueType: string; ValueName: ""; ValueData: {cm:WeblocLink}; Flags: uninsdeletekey
Root: HKCR; Subkey: "Webloc\DefaultIcon"; ValueType: string; ValueName: ""; ValueData: "{app}\{#MyAppIconsFile},3"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\open\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#MyAppExeName}"" ""%1"""; Flags: uninsdeletevalue

; Add new file .webloc
Root: HKCR; Subkey: ".webloc\ShellNew"; ValueType: string; ValueName: "ItemName"; ValueData: """{app}\{#MyAppExeName}"" ""%1"""; Flags: uninsdeletevalue
Root: HKCR; Subkey: ".webloc\ShellNew"; ValueType: string; ValueName: "FileName"; ValueData: "{app}\Template.webloc"; Flags: uninsdeletevalue
Root: HKCR; Subkey: ".webloc\ShellNew"; ValueType: string; ValueName: "NullFile"; ValueData: ""; Flags: uninsdeletevalue

; Add edit file menu
Root: HKCR; Subkey: "Webloc\shell\edit"; ValueType: string; ValueName: "icon"; ValueData: """{app}\{#MyAppIconsFile}"",7"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\edit\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#MyAppExeName}"" ""-edit"" ""%1"" "; Flags: uninsdeletevalue

; Add qr file menu
Root: HKCR; Subkey: "Webloc\shell\GenerateQRCode"; ValueType: string; ValueName: ""; ValueData: {cm:GenerateQrCode}; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\GenerateQRCode"; ValueType: string; ValueName: "icon"; ValueData: """{app}\{#MyAppIconsFile}"",5"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\GenerateQRCode\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#MyAppExeName}"" ""-qr"" ""%1"" "; Flags: uninsdeletevalue

; Add copy qr file menu
Root: HKCR; Subkey: "Webloc\shell\CopyQRCode"; ValueType: string; ValueName: ""; ValueData: {cm:CopyQRMenu}; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\CopyQRCode"; ValueType: string; ValueName: "icon"; ValueData: """{app}\{#MyAppIconsFile}"",5"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\CopyQRCode\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#MyAppExeName}"" ""-copy-qr"" ""%1"" "; Flags: uninsdeletevalue

; Add copy file menu
Root: HKCR; Subkey: "Webloc\shell\Copy"; ValueType: string; ValueName: ""; ValueData: {cm:CopyMenu}; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\Copy"; ValueType: string; ValueName: "icon"; ValueData: """{app}\{#MyAppIconsFile}"",6"; Flags: uninsdeletevalue
Root: HKCR; Subkey: "Webloc\shell\Copy\command"; ValueType: string; ValueName: ""; ValueData: """{app}\{#MyAppExeName}"" ""-copy"" ""%1"" "; Flags: uninsdeletevalue

; Add updater autorun
Root: HKCU; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\Run"; ValueType: string; ValueName: "Update"; ValueData: """start weblocopener -update-silent"""; Flags: uninsdeletevalue

; Add app settings
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: ""; ValueData: ""; Flags: uninsdeletevalue
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "auto_update_enabled"; ValueData: "true"; Flags: uninsdeletevalue createvalueifdoesntexist
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "browser"; ValueData: "default"; Flags: uninsdeletevalue createvalueifdoesntexist
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "open_folder_for_qr"; ValueData: "true"; Flags: uninsdeletevalue createvalueifdoesntexist
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "notifications"; ValueData: "true"; Flags: uninsdeletevalue createvalueifdoesntexist
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "converter_export_extension"; ValueData: "url"; Flags: uninsdeletevalue createvalueifdoesntexist
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "dark_mode"; ValueData: "21:0;7:0"; Flags: uninsdeletevalue createvalueifdoesntexist
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "locale"; ValueData: "default"; Flags: uninsdeletevalue createvalueifdoesntexist

Root: HKCU; Subkey: "SOFTWARE\{#MyAppName}\Capabilities"; ValueType: string; ValueName: "ApplicationDescription"; ValueData: "Open, edit and create .webloc links on Windows"; Flags: uninsdeletevalue
Root: HKCU; Subkey: "SOFTWARE\{#MyAppName}\Capabilities\FileAssociations"; ValueType: string; ValueName: ".webloc"; ValueData: {cm:WeblocLink}; Flags: uninsdeletevalue

; Adds start command to cmd
Root: HKLM; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\{#MyAppName}.exe"; ValueType: string; ValueName: ""; ValueData: "{app}\{#MyAppExeName}"; Flags: uninsdeletevalue
Root: HKLM; Subkey: "SOFTWARE\Microsoft\Windows\CurrentVersion\App Paths\{#MyAppName}.exe"; ValueType: string; ValueName: "Path"; ValueData: "{app}\"; Flags: uninsdeletevalue


[Registry]
;delete old entries
;Root: HKCU; Subkey: "<path>"; ValueName: "<Value>"; ValueType: none; Flags: deletevalue;
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "current_version"; ValueData: none; Flags: deletevalue;
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "install_location"; ValueData: none; Flags: deletevalue;
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "name"; ValueData: none; Flags: deletevalue;
Root: HKCU; Subkey: "SOFTWARE\JavaSoft\Prefs\{#MyAppName}"; ValueType: string; ValueName: "url_update_info"; ValueData: none; Flags: deletevalue;

[Languages]
Name: "English"; MessagesFile: "{#MyAppAdditionalPath}\languages\English.isl";
Name: "German"; MessagesFile: "{#MyAppAdditionalPath}\languages\German.isl"
Name: "French"; MessagesFile: "{#MyAppAdditionalPath}\languages\French.isl"
Name: "Italian"; MessagesFile: "{#MyAppAdditionalPath}\languages\Italian.isl"
Name: "Russian"; MessagesFile: "{#MyAppAdditionalPath}\languages\Russian.isl"


[Files]
Source: "{#MyAppSourcePath}\build\WeblocOpener.exe"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#MyAppSourcePath}\target\lib\*"; DestDir: "{app}\lib"; Flags: ignoreversion recursesubdirs
Source: "{#MyAppSourcePath}\build\Template.webloc"; DestDir: "{app}"; Flags: ignoreversion
Source: "{#ImagesPath}\icons.icl"; DestDir: "{app}"; Flags: ignoreversion

; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[InstallDelete]
;Deletes old files
Type: files; Name: "{group}\{#MyAppName} {cm:Update}.lnk";
Type: files; Name: "{commonprograms}\{#MyAppName} {cm:Update}.lnk"
Type: files; Name: "{app}\Updater.jar"
Type: files; Name: "{app}\readme.rtf"
Type: filesandordirs; Name: "{app}\lib"

Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Log\{#MyAppName}\DEBUG"
Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Log\{#MyAppName}\INFO"
Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Log\{#MyAppName}\WARN"

Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Log\Updater\DEBUG"
Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Log\Updater\INFO"
Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Log\Updater\WARN"

Type: filesandordirs; Name: "{%TEMP}\{#MyAppName}\Updater_.jar"


[Icons]
Name: "{group}\{#MyAppName} {cm:Settings}"; Filename: "{app}\{#MyAppExeName}"; OnlyBelowVersion: 6.1.9;
Name: "{commonprograms}\{#MyAppName} {cm:Settings}"; Filename: "{app}\{#MyAppExeName}";  MinVersion: 6.2.9200; 

;Name: "{group}\{cm:UninstallProgram,{#MyAppName}}"; Filename: "{uninstallexe}"; IconFilename: "{app}\{#MyAppIconsFile}"; IconIndex: 1;  Components: main

;--------------------------Windows task----------------------
[Run]
Filename: "schtasks"; \
    Parameters: "/Create /F /SC DAILY /TN ""CheckUpdatesWeblocOpener"" /TR ""'{app}\{#MyAppExeName}' -update-silent"; \
    Flags: runhidden

[UninstallRun]
Filename: "schtasks"; \
    Parameters: "/delete /tn CheckUpdatesWeblocOpener /f"; \
    Flags: runhidden
;------------------------/Windows task-----------------------

[Run]
Filename: https://benchdoos.github.io/; Description: "{cm:ProgramOnTheWeb,{#MyAppName}}"; Flags: postinstall shellexec  unchecked
