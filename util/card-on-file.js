function capitalize(str) {
  if(str.includes("_")) {
    const words = str.split("_");
    return capitalize(words[0]) + " " + capitalize(words[1]);
  }
  return str.charAt(0).toUpperCase() + str.toLowerCase().slice(1);
}

function getImageForBrand(brand) {
  // Wiring the images for each card type. 
  // If path changes or you want to support more cards, change the code here.
  const prefix = "/images/"
  const extension = ".svg";
  let name = "";
  switch(brand) {
    case "VISA": 
      name = "visa";
      break;
    case "MASTERCARD":
      name = "mastercard";
      break;
    case "AMERICAN_EXPRESS":
      name = "apex"
      break;
    default:
      break;
  }

  return prefix + name + extension;
}

module.exports = {
  capitalize,
  getImageForBrand
}