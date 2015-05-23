<%@page import="swt.wt.lab2.api.*"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.junit.experimental.categories.Categories"%>
<%@page import="java.util.List"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="de" lang="de">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>WT Quiz</title>
        <link rel="stylesheet" type="text/css" href="style/screen.css" />
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/framework.js" type="text/javascript"></script>
    </head>
    <%
        Category currentCategory = (Category)request.getAttribute("category");
        int questionCounter = (int)request.getAttribute("questionCounter"); 
        boolean[] player1answer = (boolean[]) request.getAttribute("player1answer"); 
        boolean[] player2answer = (boolean[]) request.getAttribute("player2answer"); 
    %>
    <body id="questionpage">
        <a class="accessibility" href="#question">Zur Frage springen</a>
        <header role="banner" aria-labelledby="mainheading"><h1 id="mainheading">WT Quiz</h1></header>
        <nav role="navigation" aria-labelledby="navheading">
            <h2 id="navheading" class="accessibility">Navigation</h2>
            <ul>
                <li><a id="logoutlink" title="Klicke hier um dich abzumelden" href="#" accesskey="l">Abmelden</a></li>
            </ul>
        </nav>
        
        <!-- round info -->
        <section role="main">
            <section id="roundinfo" aria-labelledby="roundinfoheading">
                <h2 id="roundinfoheading" class="accessibility">Spielerinformationen</h2>
                <div id="player1info">
                    <span id="player1name">Spieler 1</span>
                    <ul class="playerroundsummary">
                        <%
                        for(int i = 0; i<questionCounter-1; i++)
                        {
                            if(player1answer[i])
                                out.print("<li><span class=\"accessibility\">Frage " + i + ":</span><span id=\"player1answer"+ i +"\" class=\"correct\">Richtig</span></li>");
                            else
                                out.print("<li><span class=\"accessibility\">Frage " + i + ":</span><span id=\"player1answer"+ i +"\" class=\"incorrect\">Falsch</span></li>");
                        }
                        for(int i = questionCounter-1 ; i<3 ;  i++)
                        {
                            out.print("<li><span class=\"accessibility\">Frage " + i + ":</span><span id=\"player1answer"+ i +"\" class=\"unknown\">Unbekannt</span></li>");
                        }
                        %>
                    </ul>
                </div>
                <div id="player2info">
                    <span id="player2name">Spieler 2</span>
                    <ul class="playerroundsummary">
                        <%
                        for(int i = 0; i<questionCounter-1; i++)
                        {
                            if(player2answer[i])
                                out.print("<li><span class=\"accessibility\">Frage " + i + ":</span><span id=\"player2answer"+ i +"\" class=\"correct\">Richtig</span></li>");
                            else
                                out.print("<li><span class=\"accessibility\">Frage " + i + ":</span><span id=\"player2answer"+ i +"\" class=\"incorrect\">Falsch</span></li>");
                        }
                        for(int i = questionCounter-1 ; i<3 ;  i++)
                        {
                            out.print("<li><span class=\"accessibility\">Frage " + i + ":</span><span id=\"player2answer"+ i +"\" class=\"unknown\">Unbekannt</span></li>");
                        }
                        %>
                    </ul>
                </div>
                <div id="currentcategory"><span class="accessibility">Kategorie:</span>
                        <%
                        if(currentCategory != null)
                            out.print(currentCategory.getName());
                        %>
                </div>
            </section>
            
            <!-- Question -->
            <section id="question" aria-labelledby="questionheading">
                
                <form id="questionform" action="LetsPlay" method="post">
                    <h2 id="questionheading" class="accessibility">Frage</h2>
                    <p id="questiontext">
                        <%
                        int questionNumber = 0, cateNumber = 0;
                        Question currentQuestion = null;
                        if(currentCategory != null)
                        {
                            currentQuestion = (Question)request.getAttribute("question");
                            questionCounter = (int)request.getAttribute("questionCounter"); 
                            questionNumber = (int)request.getAttribute("questionNumber");
                            cateNumber = (int)request.getAttribute("categoryNumber");
                            
                            out.print(currentQuestion.getText());
                            out.print("<input type=\"hidden\" name=\"categoryNumber\" value = " + cateNumber +  " \" >");
                            out.print("<input type=\"hidden\" name=\"questionNumber\" value = " + questionNumber + " \" >");
                            out.print("<input type=\"hidden\" name=\"questionCounter\" value = " + questionCounter + " \" >");
                        }
                        
                        %>
                        
                    </p>
                    
                    <ul id="answers">
                        <%
                        if(currentQuestion != null){
                        List<Choice> allOfChoices = currentQuestion.getAllChoices();
                        for (int i = 0; i< allOfChoices.size(); i++){
                            out.print("<li><input id=\"option" + (i+1) + 
                                    "\" type=\"checkbox\" name=\"checkbox\" value = "+ allOfChoices.get(i).getId() + 
                                    " ><label for=\"option" + (i+1) + "\">");
                            out.print(allOfChoices.get(i).getText());
                            out.print("</label></li>");
                        }
                        }
                        int[] answers = (int[])request.getAttribute("answers");
                        int[] correctAnswers = (int[])request.getAttribute("correctAnswers");
                        if(answers != null || correctAnswers!= null){
                        for(int i = 0 ; i < answers.length ; i++)
                            out.print("<p>" + answers[i] + "</p>");
                        for(int i = 0 ; i <correctAnswers.length ; i++)
                             out.print("<p>" + correctAnswers[i] + "</p>");
                        }else{
                            out.print("Leerrrrrrrrr");
                        }
                        %>
                    </ul>
                    <input id="timeleftvalue" type="hidden" value="100"/>
                    <input id="next" type="submit" value="weiter" accesskey="s"/>
                </form>
            </section>
            
            <section id="timer" aria-labelledby="timerheading">
                <h2 id="timerheading" class="accessibility">Timer</h2>
                <p><span id="timeleftlabel">Verbleibende Zeit:</span> <time id="timeleft">00:30</time></p>
                <meter id="timermeter" min="0" low="20" value="100" max="100"/>
            </section>
            
            <section id="lastgame">
                <p>Letztes Spiel: Nie</p>
            </section>
        </section>

        <!-- footer -->
        <footer role="contentinfo">© 2015 WT Quiz</footer>
        
        <script type="text/javascript">
            //<![CDATA[
            
            // initialize time
            $(document).ready(function() {
                var maxtime = 30;
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                hiddenInput.val(maxtime);
                meter.val(maxtime);
                meter.attr('max', maxtime);
                meter.attr('low', maxtime/100*20);
                timeleft.text(secToMMSS(maxtime));
            });
            
            // update time
            function timeStep() {
                var hiddenInput = $("#timeleftvalue");
                var meter = $("#timer meter");
                var timeleft = $("#timeleft");
                
                var value = $("#timeleftvalue").val();
                if(value > 0) {
                    value = value - 1;   
                }
                
                hiddenInput.val(value);
                meter.val(value);
                timeleft.text(secToMMSS(value));
                
                if(value <= 0) {
                    $('#questionform').submit();
                }
            }
            
            window.setInterval(timeStep, 1000);
            
            //]]>
        </script>
    </body>
</html>
