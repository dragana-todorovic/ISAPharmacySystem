let jsonObjekat;
function reverseGeocode(coords) {
    fetch('https://nominatim.openstreetmap.org/reverse?format=json&lon=' + coords[0] + '&lat=' + coords[1])
        .then(function (response) {
            //alert(response);
            return response.json();
        }).then(function (json) {
        	$("#txtUlica").val(json["address"]["road"]);
        	$("#txtBroj").val(json["address"]["house_number"]);
        	$("#txtGrad").val(json["address"]["city"]);
        	$("#txtPostanskiBroj").val(json["address"]["postcode"]);
        	$("#txtSirina").val(json["lon"]);
        	$("#txtDuzina").val(json["lat"]);
            console.log(json);
            jsonObjekat = json;
        });
};

let pomocna = function () {
    var map = new ol.Map({
        target: 'map',
        layers: [
            new ol.layer.Tile({
                source: new ol.source.OSM()
            })
        ],
        view: new ol.View({
            center: ol.proj.fromLonLat([19.8424, 45.2541]),
            zoom: 15
        })
    });
    map.on('click', function (evt) {
    	var loc = window.location.pathname;
    	var dir = loc.substring(0, loc.lastIndexOf('/'));
    	console.log(dir)
        var coord = ol.proj.toLonLat(evt.coordinate);
        reverseGeocode(coord);
        var iconFeatures = [];
        var lon = coord[0];
        var lat = coord[1];
       
        var iconGeometry = new ol.geom.Point(ol.proj.transform([lon, lat], 'EPSG:4326', 'EPSG:3857'));
        var iconFeature = new ol.Feature({
            geometry: iconGeometry
        });
        iconFeatures.push(iconFeature);
        var vectorSource = new ol.source.Vector({
            features: iconFeatures //add an array of features
        });
        var iconStyle = new ol.style.Style({
            image: new ol.style.Icon(/** @type {olx.style.IconOptions} */({
                anchor: [0.5, 46],
                anchorXUnits: 'fraction',
                anchorYUnits: 'pixels',
                opacity: 0.95,
                src: "marker.png"
            }))
        });
        var vectorLayer = new ol.layer.Vector({
            source: vectorSource,
            style: iconStyle
        });
        map.addLayer(vectorLayer);
  
    });

};