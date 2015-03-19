
var x = 1;

function setClass(idx, className) {
    var id = 'page' + idx;
    var div = document.getElementById(id);
    div.className = className;
}

function switchPage() {
    var px = x;
    x = x + 1;
    if ( x > 2) {
	x = 1;
    }
    setClass(x, "fullpageVisible");
    setClass(px, "fullpageInvisible");
}
