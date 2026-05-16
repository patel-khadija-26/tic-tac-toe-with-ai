<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Tic Tac Toe AI</title>

<style>
body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: linear-gradient(145deg, #0a0f1f, #0d1328);
    color: white;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

/* Container */
.container {
    text-align: center;
}

/* Title */
h1 {
    font-size: 32px;
    margin-bottom: 5px;
}

.sub {
    color: #888;
    margin-bottom: 20px;
}

/* Score */
.score {
    display: flex;
    justify-content: space-around;
    margin-bottom: 20px;
}

.score div {
    font-size: 18px;
}

/* Result */
.result {
    margin-bottom: 20px;
    font-size: 20px;
    color: #ccc;
}

/* Board */
.board {
    display: grid;
    grid-template-columns: repeat(3, 90px);
    gap: 15px;
    justify-content: center;
}

.cell {
    width: 90px;
    height: 90px;
    background: #11172c;
    border-radius: 15px;
    font-size: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    cursor: pointer;
    box-shadow: 0 0 10px rgba(0,0,0,0.5);
    transition: 0.2s;
}

.cell:hover {
    background: #182040;
}

.x {
    color: white;
}

.o {
    color: #7a5cff;
}

/* Button */
button {
    margin-top: 20px;
    padding: 10px 20px;
    background: #7a5cff;
    border: none;
    border-radius: 10px;
    color: white;
    cursor: pointer;
}
</style>

</head>
<body>

<div class="container">
    <h1>Tic-Tac-Toe</h1>
    <div class="sub">vs Unbeatable AI</div>

    <div class="score">
        <div>YOU (X): <span id="userScore">0</span></div>
        <div>DRAWS: <span id="draws">0</span></div>
        <div>AI (O): <span id="aiScore">0</span></div>
    </div>

    <div class="result" id="result">Your Turn</div>

    <div class="board" id="board"></div>

    <button onclick="resetGame()">Restart</button>
</div>

<script>
let board = ["", "", "", "", "", "", "", "", ""];
let gameActive = true;

const boardEl = document.getElementById("board");
const resultEl = document.getElementById("result");

let userScore = 0, aiScore = 0, draws = 0;

function createBoard() {
    boardEl.innerHTML = "";
    board.forEach((cell, index) => {
        const div = document.createElement("div");
        div.classList.add("cell");
        div.innerHTML = cell;
        div.onclick = () => handleMove(index);
        if(cell === "X") div.classList.add("x");
        if(cell === "O") div.classList.add("o");
        boardEl.appendChild(div);
    });
}

function handleMove(index) {
    if (board[index] !== "" || !gameActive) return;

    board[index] = "X";
    createBoard();

    if (checkWinner("X")) {
        resultEl.innerText = "You Win!";
        userScore++;
        updateScore();
        gameActive = false;
        return;
    }

    if (board.every(cell => cell !== "")) {
        resultEl.innerText = "It's a Draw!";
        draws++;
        updateScore();
        gameActive = false;
        return;
    }

    aiMove();
}

function aiMove() {
    let bestScore = -Infinity;
    let move;

    for (let i = 0; i < 9; i++) {
        if (board[i] === "") {
            board[i] = "O";
            let score = minimax(board, 0, false);
            board[i] = "";
            if (score > bestScore) {
                bestScore = score;
                move = i;
            }
        }
    }

    board[move] = "O";
    createBoard();

    if (checkWinner("O")) {
        resultEl.innerText = "AI Wins!";
        aiScore++;
        updateScore();
        gameActive = false;
        return;
    }

    if (board.every(cell => cell !== "")) {
        resultEl.innerText = "It's a Draw!";
        draws++;
        updateScore();
        gameActive = false;
    }
}

function minimax(board, depth, isMaximizing) {
    if (checkWinner("O")) return 1;
    if (checkWinner("X")) return -1;
    if (board.every(cell => cell !== "")) return 0;

    if (isMaximizing) {
        let bestScore = -Infinity;
        for (let i = 0; i < 9; i++) {
            if (board[i] === "") {
                board[i] = "O";
                let score = minimax(board, depth + 1, false);
                board[i] = "";
                bestScore = Math.max(score, bestScore);
            }
        }
        return bestScore;
    } else {
        let bestScore = Infinity;
        for (let i = 0; i < 9; i++) {
            if (board[i] === "") {
                board[i] = "X";
                let score = minimax(board, depth + 1, true);
                board[i] = "";
                bestScore = Math.min(score, bestScore);
            }
        }
        return bestScore;
    }
}

function checkWinner(player) {
    const wins = [
        [0,1,2],[3,4,5],[6,7,8],
        [0,3,6],[1,4,7],[2,5,8],
        [0,4,8],[2,4,6]
    ];

    return wins.some(comb => 
        comb.every(index => board[index] === player)
    );
}

function updateScore() {
    document.getElementById("userScore").innerText = userScore;
    document.getElementById("aiScore").innerText = aiScore;
    document.getElementById("draws").innerText = draws;
}

function resetGame() {
    board = ["", "", "", "", "", "", "", "", ""];
    gameActive = true;
    resultEl.innerText = "Your Turn";
    createBoard();
}

createBoard();
</script>

</body>
</html>
