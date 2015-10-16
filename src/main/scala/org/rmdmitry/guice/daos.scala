package org.rmdmitry.guice

import scala.concurrent.Future

/**
 * Created by dmitry on 16/10/15.
 */
object CustomerDAO {
  //SELECT name FROM CustomerTable WHERE id=${id};
  def executeSQLForFetchingCustomerName(id: Long): Future[Option[String]] = Future.successful(Some(id.toString))
}

object RedisDAO {
  def put(key: String, value: String): Unit = ???

  def get(key: String): Future[Option[String]] = ???
}