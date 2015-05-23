package swt.wt.lab2.servlet;

// Your Servlet implementation

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import swt.wt.lab2.api.Category;
import swt.wt.lab2.api.Question;
import swt.wt.lab2.api.QuestionDataProvider;
import swt.wt.lab2.api.QuizFactory;
import swt.wt.lab2.api.impl.ServletQuizFactory;

public class WTQuizServlet extends HttpServlet{
    List<Category> categories;
    Category cate;
    int questionCounter = 0, questionNumber = 0, cateNumber = 0;
    boolean[] player1answer = {false, false, false};
    boolean[] player2answer = {true, false, true};
    int[] answers;
    int[] correctAnswers;
    @Override
    public void init(){
        ServletContext servletContext = getServletContext();
        QuizFactory factory = ServletQuizFactory.init(servletContext);
        QuestionDataProvider provider = factory.createQuestionDataProvider();
        categories = provider.loadCategoryData();
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException{
        //----------------- CHECK PREVIOUS ANSWER--------------------
        String[] userAnswer = request.getParameterValues("checkbox");
        
        if(userAnswer!=null){
            cateNumber = Integer.parseInt(request.getParameter("categoryNumber"));
            questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
            questionCounter = Integer.parseInt(request.getParameter("questionCounter"));
            cate = categories.get(cateNumber);
            Question question = cate.getQuestions().get(questionNumber);
            
            
            //-----------------TEST ------------
            
            correctAnswers = new int[question.getCorrectChoices().size()];
            answers = new int[userAnswer.length];
            for(int i = 0 ; i < userAnswer.length ; i++)
                answers[i] = Integer.parseInt(userAnswer[i]); 
            for(int i = 0 ; i < question.getCorrectChoices().size() ; i++)
                correctAnswers[i] = question.getCorrectChoices().get(i).getId();
            
            request.setAttribute("correctAnswers", correctAnswers);
            request.setAttribute("answers", answers);
            
            
            //-------------------------
            answers = SortierteArray(answers);
            correctAnswers = SortierteArray(correctAnswers);
            if(userAnswer.length != question.getCorrectChoices().size())
                player1answer[questionCounter-1] = false;
            else
            for(int i = 0 ; i < userAnswer.length ; i++)
            {
                if(question.getCorrectChoices().get(i).getId() != Integer.parseInt(userAnswer[i])){
                    player1answer[questionCounter-1] = false;
                    break;
                }
                player1answer[questionCounter-1] = true;
            }
        }
        
        //--------------CREATE NEW QUESTION -------------------------
        if(request.getParameter("categoryNumber") == null || 
           request.getParameter("questionNumber") == null ||
           request.getParameter("questionCounter") == null)
        {
            cateNumber = getRandomNumberFromList(categories.size());
        }else
        {
            cateNumber = Integer.parseInt(request.getParameter("categoryNumber"));
            questionNumber = Integer.parseInt(request.getParameter("questionNumber"));
            questionCounter = Integer.parseInt(request.getParameter("questionCounter"));
        }
        
        if(questionCounter == 3){
            cateNumber = getRandomNumberFromList(categories.size());
            questionCounter = 0;
        }
        
        cate = categories.get(cateNumber);
        questionNumber = getRandomNumberFromList(cate.getQuestions().size());
        Question question = cate.getQuestions().get(questionNumber);
        
        
        
        //-------------SEND INFORMATIONS-----------------
        request.setAttribute("category", cate);
        request.setAttribute("question", question);
        request.setAttribute("categoryNumber", cateNumber);
        request.setAttribute("questionNumber", questionNumber);
        questionCounter++;
        request.setAttribute("questionCounter", questionCounter);
        request.setAttribute("player1answer", player1answer);
        request.setAttribute("player2answer", player2answer);
        RequestDispatcher view = request.getRequestDispatcher("question.jsp");
        view.forward(request, response);
    }
    

    private int getRandomNumberFromList(int size){
        int rd = new Random().nextInt(size);
        return rd;
    }
    
    private int[] SortierteArray(int[] array) {
        int minIndex;
        for(int i = 0; i < array.length ; i++)
        {	 
            minIndex = i;
            for(int j = i+1 ; j<array.length ; j++){
		if(array[j] < array[minIndex])
                    minIndex = j;
            }
            if(minIndex != i){
		int temp = array[i];
		array[i] = array[minIndex];
		array[minIndex] = temp;
            }
	}
    return array;
    }
}