var x = 1;

function setClass(idx, className) {
    var id = 'page' + idx;
    var div = document.getElementById(id);
    div.className = className;
}

function switchPage() {
    setClass(x, "fullpageInvisible");
    x = x + 1;
    if ( x > 2) {
	x = 1;
    }
    setClass(x, "fullpageVisible");
}
