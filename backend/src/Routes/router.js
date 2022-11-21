const express = require("express");
const router = express.Router();
const controllers = require("../Controllers/controller");
const {
  validateUsername,
  validateRecipeName,
  validateRecipeIngredient,
  validateRecipeDirection,
  validateUserId,
  validateRecipeId,
} = require("../Middleware/requestValidator");

/**
 * End point for user log in.
 */
router.post("/login", controllers.login);

/**
 * End point to register an account.
 */
router.post("/register", [validateUsername], controllers.register);

/**
 * End point to get all recipes.
 */
router.get("/recipes", controllers.getAllRecipes);

/**
 * End point to get recipe by recipe id.
 */
router.post("/recipebyid", [validateRecipeId], controllers.getRecipeByRecipeId);

/**
 * End point to get recipe created by certain user
 * using user id.
 */
router.post("/recipebyuser", [validateUserId], controllers.getRecipeByUserId);

/**
 * End point to add recipe.
 */
router.post(
  "/addRecipe",
  [
    validateRecipeName,
    validateRecipeIngredient,
    validateRecipeDirection,
    validateUserId,
  ],
  controllers.addRecipe
);

/**
 * End point to update recipe.
 */
router.put(
  "/updateRecipe",
  [
    validateRecipeId,
    validateRecipeName,
    validateRecipeIngredient,
    validateRecipeDirection,
  ],
  controllers.updateRecipe
);

/**
 * End point to delete recipe.
 */
router.delete("/deleteRecipe", [validateRecipeId], controllers.deleteRecipe);

/**
 * End point to add recipe to user's bookmark.
 */
router.post(
  "/addBookmark",
  [validateRecipeId, validateUserId],
  controllers.addBookmark
);

/**
 * End point to get user's recipe bookmark.
 */
router.post("/getBookmark", [validateUserId], controllers.getBookmark);

/**
 * End point to delete a user's recipe bookmark.
 */
router.delete(
  "/deleteBookmark",
  [validateRecipeId, validateUserId],
  controllers.deleteBookmark
);

/**
 * End point to check user's recipe bookmark
 * with certain recipe id.
 */
router.post(
  "/checkBookmark",
  [validateRecipeId, validateUserId],
  controllers.checkBookmark
);

/**
 * End point to get user data by user id.
 */
router.post("/getUserByUserId", [validateUserId], controllers.getUserByUserId);

module.exports = router;
