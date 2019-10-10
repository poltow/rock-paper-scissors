var userName;
var strategy;

function createUserCookie() {
	var userName = document.forms["userForm"]["userName"].value;
	var strategy = document.forms["userForm"]["strategy"].value;

	document.cookie = 'cuserName' + "=" + userName + ";";
	document.cookie = 'cstrategy' + "=" + strategy + ";";
}

function resetCookies() {
	document.cookie = "cuserName= ; expires = Thu, 01 Jan 1970 00:00:00 GMT"
	document.cookie = "cstrategy= ; expires = Thu, 01 Jan 1970 00:00:00 GMT"
}

function initGame() {
	hideTables();
	loadCookies();
	createGame();
	intiRoundCounterMsg();
}

function loadCookies() {
	userName = getCookie('cuserName');
	strategy = getCookie('cstrategy');
	if ((typeof userName === 'undefined') || (typeof strategy === 'undefined')
			|| userName == "" || strategy == "") {
		alert("Enter your name and strategy to start playing");
		window.location.href = '/';
	} else {
		document.getElementById("userName").innerHTML = "USER NAME: "
				+ userName;
		document.getElementById("strategy").innerHTML = "STRATEGY: " + strategy;
	}

}

function getCookie(cname) {
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for (var i = 0; i < ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}

}

function createGame() {
	var ItemJSON = '{ "name" : "' + userName + '", "strategy" : "' + strategy
			+ '" }';
	var URL = "http://localhost:8080/api/v1/game";
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.send(ItemJSON);

}

function playRound() {
	hideTables();
	var ItemJSON = '{ "name" : "' + userName + '" }';
	URL = "http://localhost:8080/api/v1/round";
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("POST", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.send(ItemJSON);
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			getRounds();
		}
	}
}

function restartGame() {
	hideTables();
	var URL = "http://localhost:8080/api/v1/rounds/" + userName;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("DELETE", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.send(null);
	intiRoundCounterMsg();
}

function deleteGame() {
	var URL = "http://localhost:8080/api/v1/game/" + userName;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("DELETE", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.send(null);
}

function getRounds() {
	var URL = "http://localhost:8080/api/v1/rounds/" + userName;
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.open("GET", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		var rounds = JSON.parse(this.responseText);
		if (this.readyState == 4 && this.status == "200") {
			var myObj = JSON.parse(this.responseText);

			var txt = "<h3>Rounds played in current game: " + myObj.length;

			txt += "<h3>"
			txt += "<table border='1'>"
			txt += "<tr>";
			txt += "<th>Round number</th>";
			txt += "<th>You played</th>";
			txt += "<th>Your opponent played</th>";
			txt += "<th>Your result is</th>";
			txt += "</tr>";

			for (x in myObj) {
				var round = 1 + parseInt(x);
				txt += "<tr><td>" + round + "</td>";
				txt += "<td>" + myObj[x].playerShape + "</td>";
				txt += "<td>" + myObj[x].opponentShape + "</td>";
				txt += "<td>" + myObj[x].result + "</td></tr>";
			}
			txt += "</table>"
			document.getElementById("roundsTable").innerHTML = txt;

			showRoundsTable();
		}
	};
	xmlhttp.send(null);
}

function showHistory() {
	hideTables();
	var total_wins = 0;
	var total_loses = 0;
	var total_draws = 0;
	var totals = 0;
	var URL = "http://localhost:8080/api/v1/history";
	var xmlhttp = new XMLHttpRequest();

	xmlhttp.open("GET", URL, true);
	xmlhttp.setRequestHeader("Content-Type", "application/json");
	xmlhttp.onreadystatechange = function() {
		var rounds = JSON.parse(this.responseText);
		if (this.readyState == 4 && this.status == "200") {
			var myObj = JSON.parse(this.responseText);
			var txt = "<h3>Per user history<h3>"
			txt += "<table border='1'>"
			txt += "<tr>";
			txt += "<th>User name</th>";
			txt += "<th>Rounds played</th>";
			txt += "<th>Nº of wins</th>";
			txt += "<th>Nº of loses</th>";
			txt += "<th>Nº of draws</th>";
			txt += "</tr>";
			for (x in myObj) {
				txt += "<tr><td style=\"text-align:left\">"
						+ myObj[x].userName + "</td>";
				
				txt += "<td>" + myObj[x].total + "</td>";
				totals += parseInt(myObj[x].total);

				txt += "<td>" + myObj[x].wins + "</td>";
				total_wins += parseInt(myObj[x].wins);
				
				txt += "<td>" + myObj[x].loses + "</td>";
				total_loses += parseInt(myObj[x].loses);
				
				txt += "<td>" + myObj[x].draws + "</td></tr>";
				total_draws += parseInt(myObj[x].draws);
			}

			txt += "</table>"
			txt += "<h3>Total history<h3>"
			txt += "<table border='1'>"
			txt += "<tr>";
			txt += "<th>Rounds played</th>";
			txt += "<th>Nº of wins</th>";
			txt += "<th>Nº of loses</th>";
			txt += "<th>Nº of draws</th>";
			txt += "</tr>";
			txt += "<td>" + totals + "</td>";
			txt += "<td>" + total_wins + "</td>";
			txt += "<td>" + total_loses + "</td>";
			txt += "<td>" + total_draws + "</td></tr>";
			txt += "</table>"
			document.getElementById("usersHistory").innerHTML = txt;

			showHistoryTable();
		}
	};
	xmlhttp.send(null);
}

function intiRoundCounterMsg() {
	var txt = "<h3>Rounds played in current game: 0</h3>";
	document.getElementById("roundsTable").innerHTML = txt;
	document.getElementById("roundsTable").style.display = "block";
}

function showHistoryTable() {
	document.getElementById("usersHistory").style.display = "block";
}

function showRoundsTable() {
	document.getElementById("roundsTable").style.display = "block";
}

function hideTables() {
	document.getElementById("roundsTable").style.display = "none";
	document.getElementById("usersHistory").style.display = "none";
}