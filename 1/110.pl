while (<>) {
	print if /\b((\S+)\2)\b/;
}
