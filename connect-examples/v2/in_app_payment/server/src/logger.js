function getTimestamp() {
  const current_datetime = new Date();
  const year = current_datetime.getFullYear();
  const month = (current_datetime.getMonth() + 1);
  const day = current_datetime.getDate();
  const hours = current_datetime.getHours();
  const minutes = current_datetime.getMinutes();
  const seconds = current_datetime.getSeconds();
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

function logger(req, res, next) {
  const timestamp = getTimestamp();
  const method = req.method;
  const url = req.url;
  let body = '';
  if (req.body && Object.keys(req.body).length > 0) {
    body = '\n' + JSON.stringify(req.body, null, 2);
  }
  console.log(`[${timestamp}] ${method}:${url} ${body}`);
  next();
}

module.exports = logger;
