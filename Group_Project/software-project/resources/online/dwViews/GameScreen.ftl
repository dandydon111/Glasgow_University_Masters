<html>

<head>
    <!-- Web page title -->
    <title>Top Trumps</title>

    <!-- Import JQuery, as it provides functions you will probably find useful (see https://jquery.com/) -->
    <script src="https://code.jquery.com/jquery-2.1.1.js"></script>
    <script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.11.1/themes/flick/jquery-ui.css">

    <!-- Optional Styling of the Website, for the demo I used Bootstrap (see https://getbootstrap.com/docs/4.0/getting-started/introduction/) -->
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/TREC_IS/bootstrap.min.css">
    <script src="http://dcs.gla.ac.uk/~richardm/vex.combined.min.js"></script>
    <script>vex.defaultOptions.className = 'vex-theme-os';</script>
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex.css"/>
    <link rel="stylesheet" href="http://dcs.gla.ac.uk/~richardm/assets/stylesheets/vex-theme-os.css"/>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">


    <style>
        body{
            background-color: aliceblue;
        }
        #navbar{
            text-align: center;
        }
        #gameInfo{
            text-align: center;
        }
        #gameButton{
            text-align: center;
        }
        #howManyAI{
            text-align: center;

        }


    </style>
    <script>

    </script>



</head>



<body onload="initalize()"> <!-- Call the initalize method when the page loads -->
<!--header-->
<div  id="navbar">
    <h1 >Top Trumps Games</h1><br/>
    <hr>
</div>

<!--Display game infomation here-->
<div id="gameInfo">
    <p id="currentRound">Round:</p>
    <p id="drawCount">Draw Count:</p>
    <p id="communalPile">Communal Pile:</p>
    <p id="playerLeft">Players In Game:</p>
    <p id="currentPlayerName">Who's Turn:</p>
    <strong><p id="message"> </p></strong>

</div>


<!-- Game Button here-->
<div id="gameButton"><br/>
    <!-- Button: Start Game-->
    <button onclick="buttonControl()"  type="button" class="btn-primary  btn-lg " id="startButton" >Start Game!</button></a>

    <br/>
    <br/>
</div>
<hr/>

<!--card-->

<div class="container" id="allCards">
    <div class="row">

        <!--Player 1-->
        <div class="col" id="P1">
            <img class="card-img-top" id="P1CardImage" src="" alt="Card image" style="width:100%;height:200px ">
            <div id="P1Info">
                <h2 class="PlayerInfo" >You</h2><br/>
                <h4 id="P1LeftCards">Card left:</h4><hr/>
            </div>
            <div class="cardInfo" id="P1CardInfo">
                <div id="cardName">
                    <h3 id="P1CardName"></h3><hr/>

                </div>

                <p id="P1Cat1"></p>
                <p id="P1Cat2"></p>
                <p id="P1Cat3"></p>
                <p id="P1Cat4"></p>
                <p id="P1Cat5"></p><hr/>
            </div>
        </div>

        <!--AI 1-->
        <div class="col" id="A1">
            <img class="card-img-top" id="A1CardImage" src="" alt="Card image" style="width:100%;height:200px">
            <div id="A1Info">
                <h2 class="AI1Info" >AI Player 1</h2><br/>
                <h4 id="A1LeftCards">Card left:</h4><hr/>

            </div>
            <div class="cardInfo" id="A1CardInfo">
                <div id="cardName">
                    <h3 id="A1CardName"></h3><hr/>

                </div>
                <p id="A1Cat1"></p>
                <p id="A1Cat2"></p>
                <p id="A1Cat3"></p>
                <p id="A1Cat4"></p>
                <p id="A1Cat5"></p><hr/>
            </div>

        </div>

        <!--AI 2-->
        <div class="col" id="A2">
                <img class="card-img-top" id="A2CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A2Info">
                    <h2 class="A21Info" >AI Player 2</h2><br/>
                    <h4 id="A2LeftCards">Card left:</h4><hr/>

                </div>
                <div class="cardInfo" id="A2CardInfo">
                    <div id="cardName">
                        <h3 id="A2CardName"></h3><hr/>

                    </div>
                    <p id="A2Cat1"></p>
                    <p id="A2Cat2"></p>
                    <p id="A2Cat3"></p>
                    <p id="A2Cat4"></p>
                    <p id="A2Cat5"></p><hr/>
                </div>
        </div>

        <!--AI 3-->
        <div class="col" id="A3">
                <img class="card-img-top" id="A3CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A3Info">
                    <h2 class="A31Info" >AI Player 3</h2><br/>
                    <h4 id="A3LeftCards">Card left:</h4><hr/>

                </div>
                <div class="cardInfo" id="A3CardInfo">
                    <div id="cardName">
                        <h3 id="A3CardName"></h3><hr/>

                    </div>
                    <p id="A3Cat1"></p>
                    <p id="A3Cat2"></p>
                    <p id="A3Cat3"></p>
                    <p id="A3Cat4"></p>
                    <p id="A3Cat5"></p><hr/>
                </div>
        </div>

        <!--AI 4-->
        <div class="col" id="A4">
                <img class="card-img-top" id="A4CardImage" src="" alt="Card image" style="width:100%;height:200px">
                <div id="A4Info">
                    <h2 class="AI4Info" >AI Player 4</h2><br/>
                    <h4 id="A4LeftCards">Card left:</h4><hr/>

                </div>
                <div class="cardInfo" id="A4CardInfo">
                    <div id="cardName">
                        <h3 id="A4CardName"></h3><hr/>

                    </div>
                    <p id="A4Cat1"></p>
                    <p id="A4Cat2"></p>
                    <p id="A4Cat3"></p>
                    <p id="A4Cat4"></p>
                    <p id="A4Cat5"></p><hr/>
                </div>
        </div>

    </div>
</div>




<script type="text/javascript">

    //variables will be used in this file
    var gameNum = 0;
    var numAIPlayers = 0;
    var currentPlayerName = "";
    var currentRound = 0;
    var communalPileCount = 0;
    var bestAttribute = " ";
    var userSelectedCate = " ";
    var drawCount= 0 ;
    var playerLeftArray;
    var winnerName;
    var userCategoryArray ;

    // Method that is called on page load
    function initalize() {

        // --------------------------------------------------------------------------
        // You can call other methods you want to run when the page first loads here
        // --------------------------------------------------------------------------

        hiddenElements("allCards");

    }


    //=======================
    // button control
    //=======================

    function buttonControl() {
        var firstButtonName = document.getElementById("startButton").innerHTML;

        if (firstButtonName === "Start Game!" ){
            startGame();

            //change button to category selection
            setFirstButton("startButton","Category Selection");

        }else if(firstButtonName === "Category Selection") {
            selectCategory(gameNum);

        }else if (firstButtonName === "Show Winner"){

            playerLeft(gameNum);

            showWinner(gameNum);

        }else if (firstButtonName === "Next Round"){
            nextRound(gameNum);

        }else if (firstButtonName === "Game Finished"){

            gameFinished(gameNum);

        }
    }

    //===========================
    // FIRST PART I: Start Game
    //===========================

    // start game
    function startGame() {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/startGame"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            // drawCard(gameNum);

            var responseText = xhr.response; // the text of the response
            // getP1CardName();

            var basicInfo = JSON.parse(responseText);

            gameNum = basicInfo[0];
            numAIPlayers = basicInfo[1];


            showElements("P1");
            // display the user card
            displayUserCard(gameNum);

            //display the current information
            getCurrentInfo(gameNum);
            //display the left cards number
            getLeftCards(gameNum);

            //display the players in the game
            playerLeft(gameNum);
            //
            showAICards(gameNum,numAIPlayers);

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


    function showAICards(gameNum,numAIPlayers) {
        if (numAIPlayers === 3){

            hiddenCards("A4");
        }else if (numAIPlayers === 2){
            hiddenCards("A4");
            hiddenCards("A3");

        }else if (numAIPlayers === 1){
            hiddenCards("A4");
            hiddenCards("A3");
            hiddenCards("A2");

        }

    }


    //===================================
    // display card name, image
    //===================================


    //display the contents of the user's top card: set the player1 image and name
    function displayUserCard(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/displayUserCard?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here
            var p1CardName = JSON.parse(responseText);

            //set card name
            document.getElementById("P1CardName").innerHTML = p1CardName
            //set card image
            setImage("P1CardImage",p1CardName);
            //set card categories
            getUserTopCardCategories(gameNum);

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


    //displayAICard
    function displayAICard(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/displayAICard?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here
            var AICardName = JSON.parse(responseText);

            //set AI card Image
            for (var i= 0; i < AICardName.length; i++) {

                setImage("A"+(i+1)+"CardImage", AICardName[i]);

                //set AI card name
                document.getElementById("A"+(i+1)+"CardName").innerHTML = AICardName[i];

            }

            //set AI card category
            getAITopCardCategories(gameNum);

        };



        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();


    }



    //get AI TOP card categories
    function getAITopCardCategories(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getAITopCardCategories?gameNum="+gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here

            var categoryArray = JSON.parse(responseText);

            //set AI categories
            for (var i= 0; i < 4; i++) {

                var AIName = "A"+(i+1)+"Cat";

                for (var j= 0; j < 5; j++){
                    setCategory(AIName+(j+1),categoryArray[i][j]);

                }
            }

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();


    }

    //get user card categories
    function getUserTopCardCategories(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getUserTopCardCategories?gameNum="+gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //get the user category array
            userCategoryArray = JSON.parse(responseText);

            // set the user categories
            for (var i= 0; i < userCategoryArray.length; i++) {
                setCategory("P1Cat"+ (i+1), userCategoryArray[i]);
            }

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();


    }



    // ============================
    // SECOND PART II: Select Category
    // ============================

    //if the current player is AI,
    //if the current player is player1, let the player 1 choose category
    function selectCategory(gameNum) {

        // currentPlayer is AI
        if (currentPlayerName !== "You"){

            //AI player select Category
            //show AI players' card
            showElements("A1");
            showElements("A2");
            showElements("A3");
            showElements("A4");

            AISelectCategory(gameNum);

            //change the button
            setFirstButton("startButton","Show Winner");

        }else {
            // if the current player is You, show let the user to choose the category
            // if (currentPlayerName.valueOf() === "You"){
                document.getElementById("message").innerHTML = "It is your turn now, choose a category on Card";

                showElements("A1");
                showElements("A2");
                showElements("A3");
                showElements("A4");

            displayUserCard(gameNum);
                getUserTopCardCategories(gameNum);
                //User select, return a selected category to RESTAPI
                for (var i=0; i<5;i++){
                    UserSelectCategory("P1Cat" +(i+1),userCategoryArray[i] );
                }
        }
    }



    //AI player select Category
    function AISelectCategory(gameNum) {


        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/AISelectCategory?gameNum="+gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            bestAttribute = JSON.parse(responseText);
            //1.display AI card image 2. display AI card category

            //set message
            displayAICard();
            document.getElementById("message").innerHTML = currentPlayerName + " has chosen " + bestAttribute;

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


    // User Player 1 select category
    function UserSelectCategory(id,cate) {


        document.getElementById(id).addEventListener("click",function () {
            userSelectedCate = cate;


            if (id === "P1Cat1"){
                document.getElementById("message").innerHTML = "You have chosen Size";

            }else if(id === "P1Cat2"){
                document.getElementById("message").innerHTML = "You have chosen Speed";
            }else if(id === "P1Cat3"){
                document.getElementById("message").innerHTML = "You have chosen Range";

            }else if(id === "P1Cat4"){
                document.getElementById("message").innerHTML = "You have chosen Firepower";

            }else if(id === "P1Cat5"){
                document.getElementById("message").innerHTML = "You have chosen Cargo";

            }


            //when  the user selected the category, show the AI cards and change button to show winner
            displayAICard(gameNum);
            setFirstButton("startButton","Show Winner");


            //transger the category to RESTAPI
            var tc = id;
            transferCategory(tc);

        })
    }



    // deal with user selected Category
    function transferCategory(userSelectedCate) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/transferCategory?userSelectedCate="+userSelectedCate); // Request type and URL+parameters

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response; // the text of the response
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }

    // ===========================
    // THIRD PART III: Show Winner
    // ===========================

    function showWinner(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/showWinner?gameNum="+gameNum ); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            //code here
            winnerName = JSON.parse(responseText);


            // when it is a draw
            if (winnerName === "draw"){

                //set message
                document.getElementById("message").innerHTML = "This round is  a draw, Cards added to the communal pile.";

                setFirstButton("startButton", "Next Round");

            }else  {

                    document.getElementById("message").innerHTML = "The winner of this round is: " + winnerName;

                    setFirstButton("startButton", "Next Round");
            }



        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();

    }


    // ==========================
    // FOUTH PART IV: Next Round
    // ==========================
    function nextRound(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/nextRound?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;


            document.getElementById("message").innerHTML = " " ;



            if (playerLeftArray.length < 2){

                playerLeft(gameNum);
               
            }else {
                // display the user card
                displayUserCard(gameNum);
                //display the current information
                getCurrentInfo(gameNum);
                //display the left cards number
                getLeftCards(gameNum);
                //display the players in the game
                playerLeft(gameNum);

                setFirstButton("startButton","Category Selection");

            }





        };
        // document.getElementById("message").innerHTML = showMessage ;

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();


    }




    //====================================================
    //  get current Info: currentPlayerName , currentRound
    //====================================================

    function getCurrentInfo(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getCurrentInfo?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            //get the current info array
            var responseText = xhr.response;
            var currentInfo = JSON.parse(responseText);


            //get the elements: 0.currentPlayerName 1.currentRound 2.communalPileCount 3.drawCount
            currentPlayerName = currentInfo[0];
            currentRound = currentInfo[1];
            communalPileCount = currentInfo[2];
            drawCount = currentInfo[3];
            //set the elements
            document.getElementById("currentPlayerName").innerHTML = "Who's Turn: " + currentPlayerName;
            document.getElementById("currentRound").innerHTML = "Round: " + currentRound;
            document.getElementById("communalPile").innerHTML = "Communal Pile: " + communalPileCount;
            document.getElementById("drawCount").innerHTML = "Draw Count: " + drawCount;

        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }



    function getLeftCards(gameNum) {
        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/getLeftCards?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            //get the left cards array
            var responseText = xhr.response;
            var leftCardArray = JSON.parse(responseText);

            //set the card left number to webpage
            document.getElementById("P1LeftCards").innerHTML = "Card left: " + leftCardArray[0];
            document.getElementById("A1LeftCards").innerHTML = "Card left: " + leftCardArray[1];
            document.getElementById("A2LeftCards").innerHTML = "Card left: " + leftCardArray[2];
            document.getElementById("A3LeftCards").innerHTML = "Card left: " + leftCardArray[3];
            document.getElementById("A4LeftCards").innerHTML = "Card left: " + leftCardArray[4];
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }


    //game finished
    function gameFinished(gameNum) {
    
        // click the button redirect to the home page
        document.getElementById("startButton").onclick = function () {
            location.href = "http://localhost:7777/toptrumps";
        };

    }

    //player left
    function playerLeft(gameNum) {


        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/playerLeft?gameNum=" + gameNum); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;

            //code here
            playerLeftArray = JSON.parse(responseText);



            if (  playerLeftArray.length < 2 ){

                document.getElementById("message").innerHTML =  playerLeftArray.toString() + " win this game";



                setFirstButton("startButton","Game Finished");
               updateDatabase(gameNum);

            }

            document.getElementById("playerLeft").innerHTML = "Players In Game: " + playerLeftArray.toString();

            // checkPlayersLeft(gameNum);


        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }



    //=======================
    //  set methods here
    //=======================

    //set the first button name
    function setFirstButton(id,newButtonName) {

        document.getElementById(id).innerHTML = newButtonName;

    }



    // set the Category to html by id
    function setCategory(id,value) {

        document.getElementById(id).innerHTML = " " + value;


    }


    /*
    set the Image to each card
     */
    function setImage(id, imageName) {
        document.getElementById(id).src = "http://dcs.gla.ac.uk/~richardm/TopTrumps/" + imageName + ".jpg";

    }


    //hidden element by id
    function hiddenElements(id) {
        // document.getElementById(id).hidden = "hidden";
        // document.getElementById(id).hidden = true;
        document.getElementById(id).style.visibility = "hidden";

    }
    // hidden which can adjust layout
    function hiddenCards(id) {
        document.getElementById(id).hidden = true;


    }

    //show element by id
    function showElements(id) {
        // $(".id").show();
        // document.getElementById(id).hidden = false;
        document.getElementById(id).style.visibility ="visible";
    }

    

    //========================================
    // Fifth PART V: Update Database
    //========================================
     function updateDatabase(gameNum) {
       // First create a CORS request, this is the message we are going to send (a get request in this case)
         var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/updateDatabase?gameNum=" + gameNum); // Request type and URL
    
         // Message is not sent yet, but we can check that the browser supports CORS
         if (!xhr) {
             alert("CORS not supported");
         }
    
         // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
         // to do when the response arrives
         xhr.onload = function(e) {
             var responseText = xhr.response;
    
    
         };
    
         // We have done everything we need to prepare the CORS request, so send it
         xhr.send();
    
     }



    //=====================
    //  reusable method
    //=====================

    // This is a reusable method for creating a CORS request. Do not edit this.
    function createCORSRequest(method, url) {
        var xhr = new XMLHttpRequest();
        if ("withCredentials" in xhr) {

            // Check if the XMLHttpRequest object has a "withCredentials" property.
            // "withCredentials" only exists on XMLHTTPRequest2 objects.
            xhr.open(method, url, true);

        } else if (typeof XDomainRequest != "undefined") {

            // Otherwise, check if XDomainRequest.
            // XDomainRequest only exists in IE, and is IE's way of making CORS requests.
            xhr = new XDomainRequest();
            xhr.open(method, url);

        } else {

            // Otherwise, CORS is not supported by the browser.
            xhr = null;

        }
        return xhr;
    }


</script>




<!-- Here are examples of how to call REST API Methods -->
<script type="text/javascript">

    // This calls the helloJSONList REST method from TopTrumpsRESTAPI
    function helloJSONList() {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloJSONList"); // Request type and URL

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response;
            var test = JSON.parse(responseText);
            alert(test);
            // the text of the response
            alert(responseText); // lets produce an alert
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }






    // This calls the helloJSONList REST method from TopTrumpsRESTAPI
    function helloWord(word) {

        // First create a CORS request, this is the message we are going to send (a get request in this case)
        var xhr = createCORSRequest('GET', "http://localhost:7777/toptrumps/helloWord?Word="+word); // Request type and URL+parameters

        // Message is not sent yet, but we can check that the browser supports CORS
        if (!xhr) {
            alert("CORS not supported");
        }

        // CORS requests are Asynchronous, i.e. we do not wait for a response, instead we define an action
        // to do when the response arrives
        xhr.onload = function(e) {
            var responseText = xhr.response; // the text of the response
            alert(responseText); // lets produce an alert
        };

        // We have done everything we need to prepare the CORS request, so send it
        xhr.send();
    }

</script>


</body>
</html>