const services = require("../Services/services");
const { validationResult } = require("express-validator");

async function login(req, res) {
  try {
    const result = await services.login(req.body);
    res.json(result);
  } catch (err) {
    res.json(err);
  }
}

async function register(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.register(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function getAllRecipes(req, res) {
  try {
    const result = await services.getAllRecipes();
    res.json(result);
  } catch (err) {
    res.status(400).json(err);
  }
}

async function getRecipeByRecipeId(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.getRecipeByRecipeId(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function getRecipeByUserId(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.getRecipeByUserId(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function addRecipe(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.addRecipe(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function updateRecipe(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.updateRecipe(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function deleteRecipe(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.deleteRecipe(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function addBookmark(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.addBookmark(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function getBookmark(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.getBookmark(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function deleteBookmark(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.deleteBookmark(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function checkBookmark(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.checkBookmark(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function getUserByUserId(req, res) {
  try {
    const errors = validationResult(req);
    if (!errors.isEmpty()) {
      return res.status(400).json({ message: errors.array()[0].msg });
    } else {
      const result = await services.getUserByUserId(req.body);
      res.json(result);
    }
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

async function searchRecipeByBookmark(req, res) {
  try {
    const result = await services.searchRecipeByBookmark(req.body);
    res.json(result);
  } catch (err) {
    if (err.detail != null) {
      res.status(400).json({ message: err.detail });
    } else {
      res.status(400).json(err);
    }
  }
}

module.exports = {
  login,
  register,
  getAllRecipes,
  addRecipe,
  getRecipeByRecipeId,
  getRecipeByUserId,
  updateRecipe,
  deleteRecipe,
  addBookmark,
  getBookmark,
  deleteBookmark,
  checkBookmark,
  getUserByUserId,
  searchRecipeByBookmark,
};
