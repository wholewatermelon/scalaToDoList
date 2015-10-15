package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject // Inject and i18n are required to make this tutorial compliant with Play 2.4
import play.api.i18n._
  
import models.Task

//messageApi is needed to be compliant with I18n, a requirement of Play 2.4
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val taskForm = Form (
    "label" -> nonEmptyText
    )
  
  def index = Action { 
    Redirect(routes.Application.tasks)
  }

                        //implicit request required for I18n compliance
  def tasks = Action {  implicit request =>
    Ok(views.html.index(Task.all(), taskForm))
  }
  
  def newTask = TODO
  
  def deleteTask(id: Long) = TODO
  
}
