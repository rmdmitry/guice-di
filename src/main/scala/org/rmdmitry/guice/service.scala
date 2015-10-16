package org.rmdmitry.guice

import scala.concurrent.Future
import scala.concurrent.duration.Duration

/**
 * Created by dmitry on 16/10/15.
 */
trait CustomerService {
  def name(id: Long): Future[Option[String]]
}

trait CacheService {
  def put[T](key: String, value: T, ttl: Duration): Unit

  def get[T](key: String): Future[Option[T]]
}