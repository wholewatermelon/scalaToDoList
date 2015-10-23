package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import javax.inject.Inject // Inject and i18n are required to make this tutorial compliant with Play 2.4
import play.api.i18n._
import play.api.db._
  
import models.Task

//messageApi is needed to be compliant with I18n, a requirement of Play 2.4
class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

  val taskForm = Form (
    "Task:" -> nonEmptyText
    )

  val userForm = Form (
    "Name:" -> nonEmptyText
  )

  var userName = ""

  //First step will be getting the login information from the user
  def index = Action {
    Ok(views.html.index(userForm))
  }

  //Once we have the user's name, redirect them to the tasks page
  def setUser = Action { implicit request =>
   userForm.bindFromRequest.fold(
    errors => BadRequest(views.html.index(errors)),
    name => {
      userName = name
      Ok(views.html.tasks(Task.all(userName), taskForm, userName))
    }
   )
  }

                        //implicit request required for I18n compliance
  def tasks = Action {  implicit request =>
    Ok(views.html.tasks(Task.all(userName), taskForm, userName))
  }

  def newTask = Action { implicit request =>
    taskForm.bindFromRequest.fold(
      errors => BadRequest(views.html.tasks(Task.all(userName), errors, userName)),
      label => {
        Task.create(userName, label)
        Redirect(routes.Application.tasks)
      }
    )
  }

  def deleteTask(id: Long) = Action {
    Task.delete(id)
    Redirect(routes.Application.tasks)
  }
}
