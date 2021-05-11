#!/bin/bash
set -e
rm -f /snippet_sample/tmp/pids/server.pid
exec "$@"