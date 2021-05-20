package com.squareup.catalog.demo.example.clone;

/**
 * Exception thrown when cloning fails.
 */
class CloneCatalogException extends RuntimeException {
  CloneCatalogException() {
    super();
  }

  CloneCatalogException(Throwable t) {
    super(t);
  }
}
