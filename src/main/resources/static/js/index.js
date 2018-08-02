$(document).ready(function () {
    var inputGet = $("#input_get");
    var inputRawGet = $("#input_raw_get");
    inputGet.click(getMessage);
    inputRawGet.click(getRawMessage);

    function getMessage() {
        $.get("test/message", function (data, err) {
            console.log("get data: " + data);
            protobuf.load("proto/message.proto", function (err, root) {
                if (err) {
                    console.error(err);
                    throw err;
                }

                var message = root.lookupType("Person");
                var array = JSON.parse(data);
                var test = Uint8Array.from(array);
                var result = message.decode(test);
                console.log("result: " + JSON.stringify(result));
            });
        });
    }

    function getRawMessage() {
        var url = "https://raw.githubusercontent.com/wz2cool/fake-data/master/protobuff/price/price1.txt";
        var xhr = new XMLHttpRequest();
        xhr.open("GET", url, true);
        xhr.responseType = 'arraybuffer';
        xhr.onload = function (ev) {
            var responseArray = new Uint8Array(this.response);
            protobuf.load("proto/best_price.proto", function (err, root) {
                if (err) {
                    console.error(err);
                    throw err;
                }

                var message = root.lookupType("BestPriceList");
                var result = message.decode(responseArray);
                console.log("result: " + JSON.stringify(result));
            });
        };
        xhr.send();
    }

});

