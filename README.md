# Rule Based Security App

The goal of this App to integrate an existing [Rule Based Expert System](ftp://ftp.dca.fee.unicamp.br/pub/docs/vonzuben/ea072_2s06/notas_de_aula/Lecture02.pdf) found [here](https://github.com/bennapp/forwardBackwardChaining) with Android.

The app provided a quick yet effective method to adjudicate the risk associated with a device. 

Additional details can be found in Documentation folder.

The system built uses the internal settings of an Android device to assign a risk score of out of 10.

Use the folder FIS_2 to make a new project in Android Studio to test the code.

The app setup file can be found in <Project_Root>/App_Setup folder.

# Security Aspects Considered To Assign a Score

## Network Status

* Wifi Status
* Airplane Status

## Device Security

* Device Lock Status

## Software Security

* How old is the present OS
* Root Status
* Phone Encryption Status

## Application Security

* Sideloaded App Status
* High Risk Permission Status
