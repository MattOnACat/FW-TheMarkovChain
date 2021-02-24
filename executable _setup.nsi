; Markov Chain Word Generation
;
;--------------------------------

!define AppName "The Markov Chain"
!define AppVersion "1.0"
!define ShortName "MarkovChain"
!define Vendor "RoseAwen"

!include "MUI.nsh"
!include "Sections.nsh"


;--------------------------------

;Configuration

  ;General
  Name "${AppName}"
  OutFile "${ShortName}_setup.exe"

  ;Folder selection page
  InstallDir "$PROGRAMFILES\${SHORTNAME}"

  ;Get install folder from registry if available
  InstallDirRegKey HKLM "SOFTWARE\${Vendor}\${ShortName}" ""

  !define JAVA_VERSION "8"
  !define JAVA_PARAMS "com.company.Application"
  !define JAVA_LOCATION "C:\Program Files\Common Files\Oracle\Java\javapath\java.exe"

;--------------------------------

;Pages

; Uncomment the next line if you want optional components to be selectable
;  !insertmacro MUI_PAGE_COMPONENTS
  !insertmacro MUI_PAGE_DIRECTORY
  !insertmacro MUI_PAGE_INSTFILES
  ;!insertmacro MUI_PAGE_FINISH
  !insertmacro MUI_UNPAGE_CONFIRM
  !insertmacro MUI_UNPAGE_INSTFILES

;--------------------------------

Section "Installation of TheMarkovChain"

CreateDirectory "$INSTDIR\com\company\"

SetOutPath $INSTDIR\com\company\

File out\production\oldred-namegen\com\company\Application.class
File out\production\oldred-namegen\com\company\MarkovChain.class
File out\production\oldred-namegen\com\company\OccurrencesArray.class

;SetOutPath $INSTDIR\src\com\company\

;File src\com\company\Application.java
;File src\com\company\MarkovChain.java
;File src\com\company\OccurrencesArray.java

SetOutPath $INSTDIR

File readme.txt
File training_data.txt

WriteUninstaller $INSTDIR\Uninstall.exe

CreateDirectory "$SMPROGRAMS\RoseAwen"
CreateShortCut "$SMPROGRAMS\RoseAwen\Run TheMarkovChain.lnk" "${JAVA_LOCATION}" "${JAVA_PARAMS}"
CreateShortCut "$SMPROGRAMS\RoseAwen\Uninstall TheMarkovChain.lnk" "$INSTDIR\Uninstall.exe"

WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${ShortName}" "DisplayName" "${AppName}"
WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${ShortName}" "UninstallString" '"$INSTDIR\Uninstall.exe"'
WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${ShortName}" "NoModify" "1"
WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\${ShortName}" "NoRepair" "1"

MessageBox MB_OK "Installation was successful."

MessageBox MB_OK "REQUIRES JRE ${JAVA_VERSION}"
MessageBox MB_OK "expects java.exe at ${JAVA_LOCATION} IF PROGRAM DOESNT RUN, CONFIRM THIS"

SectionEnd

;--------------------------------

Section "Uninstall"

Delete $INSTDIR\Uninstall.exe
Delete $INSTDIR\com\company\Application.class
Delete $INSTDIR\com\company\MarkovChain.class
Delete $INSTDIR\com\company\OccurrencesArray.class
Delete $INSTDIR\readme.txt
Delete $INSTDIR\executable_setup.nsi
Delete $INSTDIR\training_data.txt

RMDir "$INSTDIR\com\company"
RMDir "$INSTDIR\com"
RMDir $INSTDIR

Delete "$SMPROGRAMS\RoseAwen\Run TheMarkovChain.lnk"
Delete "$SMPROGRAMS\RoseAwen\Uninstall TheMarkovChain.lnk"
RMDir "$SMPROGRAMS\RoseAwen"

DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\TheMarkovChain"
DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\TheMarkovChain"

SectionEnd

;--------------------------------