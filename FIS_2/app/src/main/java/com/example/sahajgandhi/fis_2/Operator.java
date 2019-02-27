package com.example.sahajgandhi.fis_2;

import java.util.*;
import java.io.*;

public abstract class Operator{
	public Variable first;
	public Variable second;
	public String token;

	abstract void op();
}