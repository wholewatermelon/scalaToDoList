import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._

import scala.collection.mutable.ListBuffer

import models.Task


class testTask extends FlatSpec with OneAppPerSuite {

  //Start a FakeApplication for testing.
  //See playframework.com/documentation/2.4.x/ScalaFunctionalTestingWithScalaTest
  val testApplication = FakeApplication()

  "Task.all()" should "start empty" in {
    val tasks = Task.all()
    assert(tasks.isEmpty)
  }

 "Task.create()" should "add item #1" in {
   Task.create("Make To Do List")
   val tasks = Task.all()
   assert (tasks.isEmpty == false)
   assert(tasks.contains(Task(1,"Make To Do List")))

 }

  "Task.create()" should "add item #2" in {
    Task.create("Add some items")
    val tasks = Task.all()
    assert (tasks.isEmpty == false)
    assert(tasks.contains(Task(1,"Make To Do List")))
    assert(tasks.contains(Task(2,"Add some items")))
    assert(!tasks.contains(Task(3,"Haven't added this yet")))
  }

  "Task.create()" should "add item #3" in {
    Task.create("Complete an item")
    val tasks = Task.all()
    assert (tasks.isEmpty == false)
    assert(tasks.contains(Task(1,"Make To Do List")))
    assert(tasks.contains(Task(2,"Add some items")))
    assert(!tasks.contains(Task(3,"Haven't added this yet")))
    assert(tasks.contains(Task(3,"Complete an item")))
  }

  "Task.delete()" should "delete item #2" in {
    Task.delete(2)
    val tasks = Task.all()
    assert (tasks.isEmpty == false)
    assert(tasks.contains(Task(1,"Make To Do List")))
    assert(!tasks.contains(Task(2,"Add some items")))  //no longer contains this item
    assert(tasks.contains(Task(3,"Complete an item")))
  }

  "Task.delete()" should "delete item #1" in {
    Task.delete(1)
    val tasks = Task.all()
    assert (tasks.isEmpty == false)
    assert(!tasks.contains(Task(1,"Make To Do List"))) //no longer contains this items
    assert(!tasks.contains(Task(2,"Add some items")))  //no longer contains this item
    assert(tasks.contains(Task(3,"Complete an item")))
  }

  "Task.delete()" should "delete item #3" in {
    Task.delete(3)
    val tasks = Task.all()
    assert (tasks.isEmpty)
  }

}
