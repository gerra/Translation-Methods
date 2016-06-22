$counter = 0;
$afterBlanks = 0;
while (<STDIN>) {
	if (/^\s*$/) {
		$counter++; 
	} else {
		if ($afterBlanks == 1 and $counter > 0) {
			print "\n";
		}
		s/^\s+|\s+$//g;
		s/(\s)(\1)+/\1/g;
		print;
		print "\n";
		$afterBlanks = 1;
		$counter = 0;
	}
}

