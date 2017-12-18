// https://developer.mozilla.org/en-US/docs/Web/HTML/Element/template
// Test to see if the browser supports the HTML template element by checking
// for the presence of the template element's content attribute.
if ('content' in document.createElement('template')) {

  // Instantiate the table with the existing HTML tbody
  // and the row with the template
  var t = document.querySelector('#productrow'),
  td = t.content.querySelectorAll("td");
  td[0].textContent = "1235646565";
  td[1].textContent = "Stuff";

  // Clone the new row and insert it into the table
  var tb = document.querySelector("tbody");
  var clone = document.importNode(t.content, true);
  tb.appendChild(clone);

  // Create a new row
  td[0].textContent = "0384928528";
  td[1].textContent = "Acme Kidney Beans";

  // Clone the new row and insert it into the table
  var clone2 = document.importNode(t.content, true);
  tb.appendChild(clone2);

} else {
  // Find another way to add the rows to the table because
  // the HTML template element is not supported.
}
