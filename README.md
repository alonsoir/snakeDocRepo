this is two projects in one. One on iLudus folder is an iOS app project which is able to capture barcodes using the camera
of the iphone. The library is ZBarSDK, and the app is able to communicate with server(s) using json parsing.

The other project is a J2ee app, includes a proposal of a web service implementation using apache cxf, hibernate and
a cache implemented with concurrent hash maps and threads iterating over the map. If cache misses, the system goes to db
using hibernate and then the system caches the data.

It is a maven project so launch mvn clean install tomcat7:run in order to launch server side, be sure that server and iphone are is 
on same network. If you get problems, keep me in touch

Alonso
