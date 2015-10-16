package org.rmdmitry.guice

import com.google.inject.{AbstractModule, Guice, Inject}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by dmitry on 16/10/15.
 */
object Main extends App {
  lazy val injector = Guice.createInjector(new MyInjector)

  val c = injector.getInstance(classOf[Controller])
  Await.ready(c.handleRequest(105l).map { res => println(s"Succeeded + $res") }, 5 seconds)
}

class Controller @Inject()(customerService: CustomerService) {

  def handleRequest(id: Long): Future[Option[String]] = customerService.name(id)
}

class MyInjector extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[CustomerService]).to(classOf[CustomerCachedService])
    bind(classOf[CacheService]).to(classOf[RedisCache])
  }
}