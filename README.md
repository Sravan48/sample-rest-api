## Synopsis

This is a sample case study to do load test using JMETER on a REST API server running on an application server. The Sample REST API is a rest API server which accepts http requests from Users. Users can sign-up, login and access profile.

## Motivation
	
Its a sample case study to do load test on a server api and to create rest api for User signup & login.

## Installation

	•	sample-rest-api is a springs project and which exposes rest-end points
		o	Download sample-rest-api
		o	Create .war file
		o	Deploy .war file in any application server (used JBOSS)
				Import project into eclipse
				Add server runtime environment in eclipse
				Add sample-rest-api project to server runtime environment
				Deploy the project and start server
	•	sample-rest-api/scripts contain the database schema
		o	Database name is given as casestudy and username & password are root
	•	Sample-rest-api/jmeter contain the jmeter test cases
		o	Load test case for HTTP GET
		o	Load test case for HTTP GET using timers
		o	Load test case for HTTP POST request
		o	Load test case for Database
		o	All above test cases contain load visualization graphs

## Server details

Hosted sample-rest-api in Amazon EC2 instance, following are the instance details,

Public DNS : ec2-52-40-196-69.us-west-2.compute.amazonaws.com

Public IP : 52.40.196.69

## Test Plan

Tested all below test cases on this EC2 instance, which produced following throughputs,

	•	Load test case for HTTP GET
		o	Max load without fail – 1200 Req/Sec
		o	Fails after Max Load – 1400 Req/Sec
	•	Load test case for HTTP GET using timers
		o	Max load without fail – 600 Req/Sec
		o	Fails after Max Load – 700 Req/Sec
	•	Load test case for HTTP POST request
		o	Max load without fail – 300 Req/Sec
		o	Fails after Max Load – 400 Req/Sec
	•	Load test case for Database
		o	Max load without fail – 25 Req/Sec
		o	Fails after Max Load – 30 Req/Sec
	•	All above test cases contain load visualization graphs
		o	Visualizations are embedded in JMeterCaseStudy.jmx

