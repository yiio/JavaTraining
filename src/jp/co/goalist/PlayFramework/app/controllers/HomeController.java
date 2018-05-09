package controllers;

import play.mvc.*;

import test.ReturnJson;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    
    
    public Result index() {
        
        String path = "/Users/kanaria/play_projects/employee_project/public/others/employees.csv";
        ReturnJson rj = new ReturnJson(path);
        String json = rj.makeJson();
        
        Result ok = ok(json);
        
        //return ok(views.html.index.render());
        
        return ok;
    }
    
    /*
    public Result index(){
        Result anystatus = status(448, "You are wrong!!");
        return anystatus;
    }
     */
    
    /*
    public Result index(){
        return redirect("/assets/images/favicon.png");
    }
     */
}
