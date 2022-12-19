const { check } = require("express-validator");

/**
 * Export some body request
 * validation.
 */
module.exports = {
  validateUsername: check("username")
    .notEmpty()
    .withMessage("Username can't be empty")
    .isLength({ min: 3, max: 15 })
    .withMessage("Username required min 3 and max 15 char")
    .custom((value) => !/\s/.test(value))
    .withMessage("No spaces are allowed in the username"),
  validateRecipeName: check("recipe_name")
    .notEmpty()
    .withMessage("Recipe name can't be empty")
    .isLength({ min: 3, max: 100 })
    .withMessage("Recipe name required min 3 and max 100 char")
    .custom((value) => /^\s*\S[^]*$/.test(value))
    .withMessage("Recipe name cannot contain only blankspaces"),
  validateRecipeIngredient: check("ingredient")
    .notEmpty()
    .withMessage("Ingredient can't be empty")
    .isLength({ min: 3, max: 1000 })
    .withMessage("Ingredient required min 3 and max 1000 char")
    .custom((value) => /^\s*\S[^]*$/.test(value))
    .withMessage("Ingredient cannot contain only blankspaces"),
  validateRecipeDirection: check("direction")
    .notEmpty()
    .withMessage("Direction name can't be empty")
    .isLength({ min: 3, max: 10000 })
    .withMessage("Direction required min 3 and max 10000 char")
    .custom((value) => /^\s*\S[^]*$/.test(value))
    .withMessage("Direction cannot contain only blankspaces"),
  validateUserId: check("user_id")
    .notEmpty()
    .withMessage("User id name can't be empty")
    .isNumeric()
    .withMessage("User id should be a number"),
  validateRecipeId: check("recipe_id")
    .notEmpty()
    .withMessage("Recipe id name can't be empty")
    .isNumeric()
    .withMessage("Recipe id should be a number"),
};
