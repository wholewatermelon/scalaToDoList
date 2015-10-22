package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import scala.language.postfixOps

case class Task(id: Long, label: String)

object Task {

  val task = {
    get[Long]("id") ~
    get[String]("label") map {
      case id ~ label => Task(id, label)
    }
  }

  def all(name: String): List[Task] = DB.withConnection { implicit c =>
    SQL("select * from task where name = {name}").on('name -> name).as(task *)
  }
  
  def create(name: String, label: String) {
    DB.withConnection { implicit c =>
      SQL("insert into task (label, name) values ({label}, {name})").on(
          'label -> label, 'name -> name
      ).executeUpdate()

    }
  }
  
  def delete (id: Long) {
    DB.withConnection { implicit c =>
    SQL("delete from task where id = {id}").on(
      'id -> id
      ).executeUpdate()
    }
  }

}