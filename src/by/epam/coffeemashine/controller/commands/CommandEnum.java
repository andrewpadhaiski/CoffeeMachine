package by.epam.coffeemashine.controller.commands;

import by.epam.coffeemashine.controller.commands.account.DepositCommand;
import by.epam.coffeemashine.controller.commands.account.WithdrawCommand;
import by.epam.coffeemashine.controller.commands.admin.AdminCoffeeFillCommand;
import by.epam.coffeemashine.controller.commands.admin.AdminIngredientCommand;
import by.epam.coffeemashine.controller.commands.admin.AdminIngredientFillCommand;
import by.epam.coffeemashine.controller.commands.common.LanguageCommand;
import by.epam.coffeemashine.controller.commands.common.ProfileCommand;
import by.epam.coffeemashine.controller.commands.common.CoffeeCommand;
import by.epam.coffeemashine.controller.commands.common.OrdersCommand;
import by.epam.coffeemashine.controller.commands.security.LoginCommand;
import by.epam.coffeemashine.controller.commands.security.LogoutCommand;
import by.epam.coffeemashine.controller.commands.security.RegisterCommand;
import by.epam.coffeemashine.controller.commands.user.UserIngredientCommand;
import by.epam.coffeemashine.controller.commands.user.UserOrderCommand;
import by.epam.coffeemashine.controller.commands.user.UserConfirmCommand;
import by.epam.coffeemashine.model.data.entities.enums.UserRoleEnum;

public enum CommandEnum {
	LANGUAGE {
		{
			this.command = new LanguageCommand();
			this.role = UserRoleEnum.GUEST;
		}
	},
	LOGIN {
		{
			this.command = new LoginCommand();
			this.role = UserRoleEnum.GUEST;
		}
	},
	REGISTER {
		{
			this.command = new RegisterCommand();
			this.role = UserRoleEnum.GUEST;
		}
	},
	LOGOUT {
		{
			this.command = new LogoutCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	DEPOSIT {
		{
			this.command = new DepositCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	WITHDRAW {
		{
			this.command = new WithdrawCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	PROFILE {
		{
			this.command = new ProfileCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	COFFEE {
		{
			this.command = new CoffeeCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	ORDERS {
		{
			this.command = new OrdersCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	USER_INGREDIENT {
		{
			this.command = new UserIngredientCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	USER_ORDER {
		{
			this.command = new UserOrderCommand();
			this.role = UserRoleEnum.USER;
		}
	},
	USER_CONFIRM {
		{
			this.command = new UserConfirmCommand();
			this.role = UserRoleEnum.USER;
		}
	},	
	ADMIN_COFFEE_FILL {
		{
			this.command = new AdminCoffeeFillCommand();
			this.role = UserRoleEnum.ADMIN;
		}
	},
	ADMIN_INGREDIENT {
		{
			this.command = new AdminIngredientCommand();
			this.role = UserRoleEnum.ADMIN;
		}
	},
	ADMIN_INGREDIENT_FILL {
		{
			this.command = new AdminIngredientFillCommand();
			this.role = UserRoleEnum.ADMIN;
		}
	};

	ICommand command = null;
	UserRoleEnum role = null; 

	public ICommand getCurrentCommand() {
		return command;
	}
	
	public UserRoleEnum getUserRole() {
		return role;
	}
}
