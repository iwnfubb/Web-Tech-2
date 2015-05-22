package swt.wt.lab2.servlet;

// Your Servlet implementation

import java.io.IOException;
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
        Category cate = getRandomCategory(categories);
        Question question = getRandomQuestion(cate);
        request.setAttribute("category", cate);
        request.setAttribute("question", question);
        RequestDispatcher view = request.getRequestDispatcher("question.jsp");
        view.forward(request, response);
        
    }
    private Category getRandomCategory(List<Category> categories){
        int rd = new Random().nextInt(categories.size());
        return categories.get(rd);
    }
    private Question getRandomQuestion(Category category){
        int rd = new Random().nextInt(category.getQuestions().size());
        return category.getQuestions().get(rd);
    }
}