package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject
import play.api.i18n._
  
import models.Task
    
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val taskForm = Form (
    "label" -> nonEmptyText
    )
  
  def index = Action { 
    Redirect(routes.Application.tasks)
  }

  def tasks = Action {  implicit request =>
    Ok(views.html.index(Task.all(), taskForm))
  }
  
  def newTask = TODO
  
  def deleteTask(id: Long) = TODO
  
}
