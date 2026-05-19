const cells = document.querySelectorAll(".cell");
const statusText = document.getElementById("status");
const restartBtn = document.getElementById("restart");

let currentPlayer = "X";
let gameActive = true;

const winPatterns = [
    [0,1,2],
    [3,4,5],
    [6,7,8],
    [0,3,6],
    [1,4,7],
    [2,5,8],
    [0,4,8],
    [2,4,6]
];

cells.forEach(cell => {
    cell.addEventListener("click", handleClick);
});

restartBtn.addEventListener("click", restartGame);

function handleClick() {
    if (this.textContent !== "" || !gameActive) {
        return;
    }

    this.textContent = currentPlayer;
    this.style.color = currentPlayer === "X" ? "red" : "blue";

    if (checkWinner()) {
        statusText.textContent = `Player ${currentPlayer} Wins!`;
        gameActive = false;
        return;
    }

    if (isDraw()) {
        statusText.textContent = "It's a Draw!";
        gameActive = false;
        return;
    }

    currentPlayer = currentPlayer === "X" ? "O" : "X";
    statusText.textContent = `Player ${currentPlayer}'s Turn`;
}

function checkWinner() {
    for (let pattern of winPatterns) {
        let [a, b, c] = pattern;

        if (
            cells[a].textContent &&
            cells[a].textContent === cells[b].textContent &&
            cells[b].textContent === cells[c].textContent
        ) {
            cells[a].style.background = "yellow";
            cells[b].style.background = "yellow";
            cells[c].style.background = "yellow";
            return true;
        }
    }
    return false;
}

function isDraw() {
    return [...cells].every(cell => cell.textContent !== "");
}

function restartGame() {
    cells.forEach(cell => {
        cell.textContent = "";
        cell.style.background = "white";
    });

    currentPlayer = "X";
    gameActive = true;
    statusText.textContent = "Player X's Turn";
}
