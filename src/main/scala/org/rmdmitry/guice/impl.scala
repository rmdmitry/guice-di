package org.rmdmitry.guice

import com.google.inject.Inject

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by dmitry on 16/10/15.
 */
class CustomerCachedService @Inject() (cacheService: CacheService) extends CustomerService {
  override def name(id: Long): Future[Option[String]] = cacheService.get[String](id.toString) flatMap {
    case Some(name) => Future.successful(Some(name))
    case None =>
      CustomerDAO.executeSQLForFetchingCustomerName(id) map {
        case Some(name) =>
          cacheService.put(id.toString, name, 60 minutes)
          Some(name)
        case None => None
      }
  }
}

class RedisCache extends CacheService {
  override def put[T](key: String, value: T, ttl: Duration): Unit = ???

  override def get[T](key: String): Future[Option[T]] = Future.successful(Option(key.asInstanceOf[T]))
}
