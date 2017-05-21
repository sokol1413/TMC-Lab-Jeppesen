var map;
var mercator = new OpenLayers.Projection("EPSG:900913");
var geographic = new OpenLayers.Projection("EPSG:4326");
//var webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/infosource/");
var webSocket;

function onStart() {
	var options = {
		projection: mercator,
		displayProjection: geographic,
		units: "m",
		maxResolution: 156543.0339,
		maxExtent: new OpenLayers.Bounds(-20037508.34, -20037508.34, 20037508.34, 20037508.34),
		numZoomLevels: 1
	};
	map = new OpenLayers.Map('map', options);
	var osm = new OpenLayers.Layer.OSM();
	var gmap = new OpenLayers.Layer.Google("Google", {
		sphericalMercator: true
	});
	markers = new OpenLayers.Layer.Markers("points");
	map.addLayers([osm, gmap]);
	var polyline = new OpenLayers.Layer.Vector("Routes");
	map.addLayer(polyline);
	map.addLayer(markers);
	directionsService = new google.maps.DirectionsService();
	map.addControl(new OpenLayers.Control.LayerSwitcher());
	map.addControl(new OpenLayers.Control.MousePosition());
	map.setCenter(new OpenLayers.LonLat(18.4718, 54.37889).transform(geographic, mercator), 16);
	var events = map.events;

};

function registerGateUpdates() {
	//Establish the WebSocket connection and set up event handlers	
	webSocket = new WebSocket("ws://localhost:80/infosource");
	webSocket.onmessage = function (msg) { 
		if(msg.data == 'new gate') getUpdate() };
}

function getGates() // get list of available gates
// return list of gates {id: idval, lon: lonval, lat: latval}
{
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "http://localhost:80/gates",
		success: function(data) {
			console.log(data);
			//here we add markers on map
			addMarkers(data);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log(' Error in processing!');

		}
	});

}

function refreshMarkerLabels() {
	for (var i = 0; i < markers.markers.length; i++) {
		if (markers.markers[i].feature.popup) {
			markers.markers[i].feature.popup.destroy();
			markers.markers[i].feature.popup = null;
		}
	}
}

function addMarkers(gates) {
	for (var i = 0; i < gates.length; i++) {
		addMarker(new OpenLayers.LonLat(gates[i].lon, gates[i].lat).transform(geographic, mercator), gates[i].id, false);
	}
}

function addMarker(lonlat, gateId, isFinal) {
	var feature = new OpenLayers.Feature(markers, lonlat);
	feature.closeBox = true;
	feature.popupClass = OpenLayers.Popup.FramedCloud;
	feature.data.overflow = "auto";

	var marker = feature.createMarker();
	marker.id = gateId;
	marker.feature = feature;
	markers.addMarker(marker);
	if (!isFinal) {
		feature.data.popupContentHTML = '<input type="button" value="Choose gate ' + marker.id + '" onclick="sendChoosenGate(' + marker.id + ')">';
		var markerClick = function(evt) {
			refreshMarkerLabels();
			if (!this.popup) {
				this.popup = this.createPopup(this.closeBox);
				map.addPopup(this.popup);
				this.popup.show();
			}
			currentPopup = this.popup;
			OpenLayers.Event.stop(evt);
		};
		marker.events.register("mousedown", feature, markerClick);
	} else {
		marker.setUrl('FinalMarker.png');
		feature.data.popupContentHTML = '<input type="button" value="Reset Gates" onclick="resetGates()">';
		var markerClick = function(evt) {
			refreshMarkerLabels();
			if (!this.popup) {
				this.popup = this.createPopup(this.closeBox);
				map.addPopup(this.popup);
				this.popup.show();
			}
			currentPopup = this.popup;
			OpenLayers.Event.stop(evt);
		};
		marker.events.register("mousedown", feature, markerClick);
	}
}

function resetGates() {
	refreshMarkerLabels();
	getGates();	
}


function addChosenGateMarker(gate) {
	markers.clearMarkers();
	addMarker(new OpenLayers.LonLat(gate.lon, gate.lat).transform(geographic, mercator), gate.id, true);
}

function sendChoosenGate(id) //send id of choosen gate
{
	$.ajax({
		type: 'POST',
		url: "http://localhost:80/gates",
		data: String(id),
		dataType: "text",
		success: function(data) {
			//if we successfuly sent the gate id to server, then we want to the send this gate from server to officer, that's why we call "getUpdate()" here
			refreshMarkerLabels();			
			console.log(data);
		}
	});

}

function getUpdate() {
	// we want to get information when some gate has been chosen
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "http://localhost:80/cgate",
		success: function(chosenGate) {
			console.log(chosenGate);
			//after we receive the gate from server, we want to display it on our map 
			addChosenGateMarker(chosenGate);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log('No gate has been chosen yet');

		}
	});
}
