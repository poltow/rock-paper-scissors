package com.rps.model.user;

import com.rps.model.player.Strategy;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {

	private String name;
	private Strategy strategy;

}
