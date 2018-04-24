# local-radio-proxy

Work in progress!

Config file can be passed as startup argument. Otherwise it's expected in the launching path as `config.xml`. 

Example:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<config>
	<settings>
		<port>12345</port>
		<defaultuseragent>Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:59.0)</defaultuseragent>
		<defaultreferer>http://www.duckduckgo.com</defaultreferer>
		<proxymode>false</proxymode>
		<proxyhost>my.proxy.host</proxyhost>
		<proxyport>3128</proxyport>
	</settings>
	<radiostations>	
		<radiostation>
			<name>BigFM</name>
			<address>http://srv05.bigstreams.de/bigfm-mp3-96</address>
			<icy>yes</icy>
		</radiostation>
		<radiostation>
			<name>Another radio station</name>
			<address>http://another.radiostation.invalid:31337/awesome_station</address>
			<icy>yes</icy>
			<user-agent>Custom user agent</user-agent>
			<referer>http://i.came.from.here.invalid</referer>
		</radiostation>
	</radiostations>
</config>
```
