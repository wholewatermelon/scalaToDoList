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
  var testNum = 1;

  s"Test# ${testNum}: Task.all(User One)" should "start empty" in {
    val tasks = Task.all("User One")
    assert(tasks.isEmpty)
  }


  testNum += 1

  s"Test# ${testNum}: Task.create(User One, Make To Do List)" should "add id #1: 'Make To Do List' to User One's List" in {
    Task.create("User One", "Make To Do List")
    val tasks = Task.all("User One")
    assert(tasks.isEmpty == false)
    assert(tasks.contains(Task(1, "Make To Do List")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.create(User One, Add some items)" should "add id #2: 'Add some items' to User One's List" in {
    Task.create("User One", "Add some items")
    val tasks = Task.all("User One")
    assert(tasks.isEmpty == false)
    assert(tasks.contains(Task(1, "Make To Do List")))
    assert(tasks.contains(Task(2, "Add some items")))
    assert(!tasks.contains(Task(3, "Haven't added this yet")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.create(User One, Complete an item)" should "add id #3: 'Complete an item' to User One's List" in {
    Task.create("User One", "Complete an item")
    val tasks = Task.all("User One")
    assert(tasks.isEmpty == false)
    assert(tasks.contains(Task(1, "Make To Do List")))
    assert(tasks.contains(Task(2, "Add some items")))
    assert(!tasks.contains(Task(3, "Haven't added this yet")))
    assert(tasks.contains(Task(3, "Complete an item")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.delete(2)" should "delete id #2: 'Add some items'" in {
    Task.delete(2)
    val tasks = Task.all("User One")
    assert(tasks.isEmpty == false)
    assert(tasks.contains(Task(1, "Make To Do List")))
    assert(!tasks.contains(Task(2, "Add some items"))) //no longer contains this item
    assert(tasks.contains(Task(3, "Complete an item")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.delete(1)" should "delete id #1: 'Make To Do List'" in {
    Task.delete(1)
    val tasks = Task.all("User One")
    assert(tasks.isEmpty == false)
    assert(!tasks.contains(Task(1, "Make To Do List"))) //no longer contains this items
    assert(!tasks.contains(Task(2, "Add some items"))) //no longer contains this item
    assert(tasks.contains(Task(3, "Complete an item")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.delete(3)" should "delete id #3: 'Complete an item'" in {
    Task.delete(3)
    val tasks = Task.all("User One")
    assert(tasks.isEmpty)
  }

  testNum += 1

  s"Test# ${testNum}: Task.create(Homer Simpson, Eat Donuts)" should "add id #4: 'Eat Donuts' to Homer Simpson's list" in {
    Task.create("Homer Simpson", "Eat Donuts")
    val tasks = Task.all("Homer Simpson")
    assert(tasks.contains(Task(4, "Eat Donuts")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.create(Homer Simpson, Sit on Couch)" should "add id #5: 'Sit on Couch' to Homer Simpson's list" in {
    Task.create("Homer Simpson", "Sit on Couch")
    val tasks = Task.all("Homer Simpson")
    assert(tasks.contains(Task(4, "Eat Donuts")))
    assert(tasks.contains(Task(5, "Sit on Couch")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.create(Marge Simpson, Sit on Couch)" should "add id #6: 'Sit on Couch' to Marge Simpson's list" in {
    Task.create("Marge Simpson", "Sit on Couch")
    val tasks = Task.all("Marge Simpson")
    assert(tasks.contains(Task(6, "Sit on Couch")))
    assert(!tasks.contains(Task(4, "Eat Donuts")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.delete(5)" should "delete id #5: 'Sit on Couch' from Homer Simpson's list only" in {
    Task.delete(5)
    val homerTasks = Task.all("Homer Simpson")
    assert(homerTasks.contains(Task(4, "Eat Donuts")))
    assert(!homerTasks.contains(Task(5, "Sit on Couch")))
    val margeTasks = Task.all("Marge Simpson")
    assert(margeTasks.contains(Task(6, "Sit on Couch")))
  }

  testNum += 1

  s"Test# ${testNum}: Task.create(Marge Simpson, Dye Hair)" should "add id #7: 'Dye Hair' to Marge Simpson's list" in {
    Task.create("Marge Simpson", "Dye Hair")
    val margeTasks = Task.all("Marge Simpson")
    assert(margeTasks.contains(Task(6, "Sit on Couch")))
    assert(margeTasks.contains(Task(7, "Dye Hair")))
    val homerTasks = Task.all("Homer Simpson")
    assert(!homerTasks.contains(Task(7, "Dye Hair")))
    assert(homerTasks.contains(Task(4, "Eat Donuts")))
  }

}
