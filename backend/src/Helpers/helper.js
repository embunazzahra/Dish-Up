const bcrypt = require("bcryptjs");
var passwordValidator = require("password-validator");

/**
 * Function to hash the user password
 * using bcrypt.
 *
 * @param {String} password the password to be hashed.
 * @returns hashed password.
 */
async function hashPassword(password) {
  const hashedPassword = await bcrypt.hash(password, 8);
  return hashedPassword;
}

/**
 * Function to compare a plain string password (user input)
 * with hashed password (from database).
 *
 * @param {String} password plain password
 * @param {String} hashedPassword hashed password
 * @returns the result of comparison.
 */
async function comparePassword(password, hashedPassword) {
  return await bcrypt.compare(password, hashedPassword);
}

/**
 * Function to validate if
 * the email address match with email validation regex.
 *
 * @param {String} emailAdress the email to validate.
 * @returns true if match, false otherwise.
 */
function validateEmail(emailAdress) {
  let regexEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
  if (emailAdress.match(regexEmail)) {
    return true;
  } else {
    return false;
  }
}

/**
 * Function to validate user's password
 * with some requirement.
 *
 * @param {String} password the password to validate.
 * @returns
 */
function validatePassword(password) {
  var schema = new passwordValidator();
  schema
    .is()
    .min(8) // Minimum length 8
    .is()
    .max(20) // Maximum length 20 (include)
    .has()
    .uppercase() // Must have uppercase letters
    .has()
    .lowercase() // Must have lowercase letters
    .has()
    .digits(1) // Must have digit
    .has()
    .not()
    .spaces();
  return schema.validate(password)
    ? true
    : schema.validate(password, { details: true });
}

module.exports = {
  hashPassword,
  comparePassword,
  validateEmail,
  validatePassword,
};
