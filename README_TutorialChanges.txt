In the ”Prepare the Task Model" to the ”Rendering the first page” portion of the tutorial, make the following changes:

in Application.scala:

add:

import javax.inject.Inject
import play.api.i18n._


And make the following changes:

class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {

...

def tasks = Action {  implicit request =>
    Ok(views.html.index(Task.all(), taskForm))
  }


in index.scala.html, change the first line to:
@(tasks: List[Task], taskForm: Form[String])(implicit messages:Messages)


without these changes you get the error:

“could not find implicit value for parameter messages: play.api.i18n.Messages”




