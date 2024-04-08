export const formattedDate = (dateString: string) => {
  const date = new Date(dateString).toUTCString();
  const [day, month, dayNum, year] = date.split(" ");
  return `${day} ${dayNum} ${month} ${year}`;
};
