package edu.ycp.cs482.Trivia.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import edu.ycp.cs482.Model.Question;
import edu.ycp.cs482.Trivia.JSON.JSON;
import edu.ycp.cs482.Trivia.controller.AddQuestion;
import edu.ycp.cs482.Trivia.controller.ChangeQuestion;
import edu.ycp.cs482.Trivia.controller.ChangeStatus;
import edu.ycp.cs482.Trivia.controller.DeleteQuestion;
import edu.ycp.cs482.Trivia.controller.GetAllQuestion;
import edu.ycp.cs482.Trivia.controller.GetQuestion;
import edu.ycp.cs482.Trivia.controller.changeAnswer1;
import edu.ycp.cs482.Trivia.controller.changeAnswer2;
import edu.ycp.cs482.Trivia.controller.changeAnswer3;
import edu.ycp.cs482.Trivia.controller.changeAnswer4;
import edu.ycp.cs482.Trivia.controller.changeFinalAnswer;
import edu.ycp.cs482.Trivia.controller.getAllQusetionPending;
import edu.ycp.cs482.Trivia.controller.getRandomQuestion;

public class QuestionPage extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private getRandomQuestion randomQuestion = new getRandomQuestion();
	private getAllQusetionPending getallquestionpending = new getAllQusetionPending();
	private GetAllQuestion getallquestion = new GetAllQuestion();
	private GetQuestion getquestion = new GetQuestion();
	private AddQuestion addquestion = new AddQuestion();
	private DeleteQuestion deletequestion = new DeleteQuestion();
	private ChangeQuestion changequestion = new ChangeQuestion();
	private changeAnswer1 changeanswer1 = new changeAnswer1();
	private changeAnswer2 changeanswer2 = new changeAnswer2();
	private changeAnswer3 changeanswer3 = new changeAnswer3();
	private changeAnswer4 changeanswer4 = new changeAnswer4();
	private changeFinalAnswer changefinalAnswer = new changeFinalAnswer();
	private ChangeStatus changestatus = new ChangeStatus();
	private Question question;
	private int id;
	private String[] tokens;
	private String pathInfo, q, a1, a2, a3, a4, af;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), getallquestion.getallQuestion());
			return ;
		}
		
		// Get the user name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		
		if(pathInfo.contains("pending")){
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), getallquestionpending.GetallQuestionPending());
			return ;
		}	
		if(pathInfo.contains("random")){
			resp.setStatus(HttpServletResponse.SC_OK);
			
			// Set status code and content type
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			
			// Return the item in JSON format
			JSON.getObjectMapper().writeValue(resp.getWriter(), randomQuestion.RandomQuestion());
			return ;
		}
		id = Integer.parseInt(pathInfo);	
		question = getquestion.getQuestion(id);								// Use a GetUsercontroller to find the user in the database
		if (question == null) {
			// No such item, so return a NOT FOUND response
			resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
			resp.setContentType("text/plain");
			resp.getWriter().println("No Question " + pathInfo);
			return;
		}
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), question);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		question = JSON.getObjectMapper().readValue(req.getReader(), Question.class);
		// Use a GetUser controller to find the item in the database
		addquestion.addQuestion(question);
		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// writing the operation out.
		JSON.getObjectMapper().writeValue(resp.getWriter(), question);
	}

	
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.setContentType("text/plain");
			resp.getWriter().println("No qusetion listed");
			return;
		}
		// Get the item name
		if (pathInfo.startsWith("/")){
			pathInfo = pathInfo.substring(1);
		}
		
		id = Integer.parseInt(pathInfo);
		deletequestion.deleteQuestion(id);

		// Set status code and content type
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.setContentType("application/json");
		
		// Return the item in JSON format
		JSON.getObjectMapper().writeValue(resp.getWriter(), pathInfo);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, JsonGenerationException, JsonMappingException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || pathInfo.equals("") || pathInfo.equals("/")) {
			resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			resp.setContentType("application/json");
			return;
		}else{
			// Get the item name
			if (pathInfo.startsWith("/")){
				pathInfo = pathInfo.substring(1);
			}	
			
			if (pathInfo.contains("/question=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);
				q = tokens[1].substring(tokens[1].indexOf('=')+1, tokens[1].length());
				
				if(q.contains("_")){
		            q = q.replaceAll("_", " ");
		        }

				changequestion.changeQuestion(id, q);
				
				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), q);
				return;
			}	
			if (pathInfo.contains("/answer1=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);
				a1 = tokens[1].substring(tokens[1].indexOf('=')+1, tokens[1].length());			
		        if(a1.contains("_")){
		            a1 = a1.replaceAll("_", " ");
		        }
				changeanswer1.changeanswer1(id, a1);

				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), a1);
				return;
			}	
			if (pathInfo.contains("/answer2=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);
				a2 = tokens[1].substring(tokens[1].indexOf('=')+1, tokens[1].length());			
		        if(a2.contains("_")){
		            a2 = a2.replaceAll("_", " ");
		        }
				changeanswer2.changeanswer2(id, a2);

				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), a2);
				return;
			}	
			if (pathInfo.contains("/answer3=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);
				a3 = tokens[1].substring(tokens[1].indexOf('=')+1, tokens[1].length());			
		        if(a3.contains("_")){
		            a3 = a3.replaceAll("_", " ");
		        }
				changeanswer3.changeanswer3(id, a1);

				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), a3);
				return;
			}	
			if (pathInfo.contains("/answer4=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);
				a4 = tokens[1].substring(tokens[1].indexOf('=')+1, tokens[1].length());			
		        if(a4.contains("_")){
		            a4 = a4.replaceAll("_", " ");
		        }
				changeanswer4.changeanswer4(id, a4);

				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), a4);
				return;
			}	
			if (pathInfo.contains("/answerfinal=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);
				af = tokens[1].substring(tokens[1].indexOf('=')+1, tokens[1].length());			
		        if(af.contains("_")){
		            af = a1.replaceAll("_", " ");
		        }
				changefinalAnswer.changefinalanswer(id, af);

				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), af);
				return;
			}	
			if (pathInfo.contains("/status=")){
				tokens = pathInfo.split("/");
				id = Integer.parseInt(tokens[0]);

				changestatus.changestatus(id);

				// Set status code and content type
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("application/json");
				
				// writing the operation out.
				JSON.getObjectMapper().writeValue(resp.getWriter(), id);
				return;
			}	
			
			
		}
	}
}
