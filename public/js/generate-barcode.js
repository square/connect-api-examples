/**
 * This file contains functionality to create a Square gift card barcode based on the gan.
 * This barcode can be used for redeeming gift cards at Square POS.
 */

function generateBarCode(elementId, value) {
  const squareGiftCardBarcodeId = "sqgc://" + value;
  let canvas = document.createElement('canvas');
  let options = {
    bcid: 'pdf417',        // Barcode type
    text: squareGiftCardBarcodeId,    // Text to encode
    scale: 2,               // 3x scaling factor
    height: 10,              // Bar height, in millimeters
    includetext: true,            // Show human-readable text
    textxalign: 'center',        // Always good to set this
  }
  bwipjs.toCanvas(canvas, options);
  document.getElementById(elementId).src = canvas.toDataURL('image/png');
}
