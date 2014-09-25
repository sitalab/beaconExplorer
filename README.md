BeaconExplorer
==============

The BeaconExplorer is a demo Google Glass app showing how to call the [SITA iBeacon Common Use Registry](https://www.developer.aero/BeaconRegistry).


What it does
============

The app is a simple use case showing how to use the two key APIs from the SITA iBeacon Common Use Registry. 

- Get list of beacons at an airport
- Get details about a beacon.

The first API is called on app startup. The second API is called when the user comes into proximity of a iBeacon, to get the meta-details for that iBeacon.

FAQ
===
- Can I get access to the iBeacons deployed at airports
  - Currently access is still limted to airlines, airports and ground handlers. The plan is to open access to general 3rd parties in the future. 

- How do I build the project
  - To build the project, check out the code and import the .project into Eclipse. It is assumed that you have Android SDK with [Google Glass GDK](https://developers.google.com/glass/) already installed.  


Contributors
============
* [ahariss](https://github.com/ahariss) 
* [kosullivansita](https://github.com/kosullivansita) / [Kevin O'Sullivan](http://www.sita.aero/surveys-reports/sita-lab)

License
=======

This project is licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

